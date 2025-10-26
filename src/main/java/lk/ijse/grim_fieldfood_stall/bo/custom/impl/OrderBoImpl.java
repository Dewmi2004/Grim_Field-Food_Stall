package lk.ijse.grim_fieldfood_stall.bo.custom.impl;

import lk.ijse.grim_fieldfood_stall.bo.custom.OrderBo;
import lk.ijse.grim_fieldfood_stall.dao.DaoFactory;
import lk.ijse.grim_fieldfood_stall.dao.custom.FoodDao;
import lk.ijse.grim_fieldfood_stall.dao.custom.OrderDao;
import lk.ijse.grim_fieldfood_stall.dao.custom.OrderFoodDao;
import lk.ijse.grim_fieldfood_stall.dto.OrderDto;
import lk.ijse.grim_fieldfood_stall.entity.Food;
import lk.ijse.grim_fieldfood_stall.entity.OrderEntity;
import lk.ijse.grim_fieldfood_stall.entity.OrderFood;
import lk.ijse.grim_fieldfood_stall.model.CartTm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderBoImpl implements OrderBo {
    private final OrderDao orderDao = (OrderDao) DaoFactory.getInstance().getDAO(DaoFactory.DAOtypes.ORDER);
    private final FoodDao foodDao = (FoodDao) DaoFactory.getInstance().getDAO(DaoFactory.DAOtypes.FOOD);
    private final OrderFoodDao orderFoodDao = (OrderFoodDao) DaoFactory.getInstance().getDAO(DaoFactory.DAOtypes.ORDERFOOD);

    @Override
    public boolean placeOrder(OrderDto dto) throws Exception {
        OrderEntity order = new OrderEntity(dto.getDate(), dto.getTotalAmount());
        boolean saved = orderDao.save(order);
        if (!saved) return false;

        for (CartTm item : dto.getCartList()) {
            if (item.getName().equalsIgnoreCase("Package")) {
                Food chips = foodDao.findByName("chips");
                Food drink = foodDao.findByName("drink");
                Food corn = foodDao.findByName("pop corn");

                if (chips != null && drink != null && corn != null) {
                    saveOrderFood(order, chips, item.getQuantity());
                    saveOrderFood(order, drink, item.getQuantity());
                    saveOrderFood(order, corn, item.getQuantity());

                    reduceStock(chips, Integer.parseInt(item.getQuantity()));
                    reduceStock(drink, Integer.parseInt(item.getQuantity()));
                    reduceStock(corn, Integer.parseInt(item.getQuantity()));
                }
            } else {
                Food foodEntity = foodDao.findById(item.getFoodId());
                if (foodEntity == null) continue;
                saveOrderFood(order, foodEntity, item.getQuantity());
                reduceStock(foodEntity, Integer.parseInt(item.getQuantity()));
            }
        }

        return true;
    }

    private void saveOrderFood(OrderEntity order, Food food, String quantity) throws Exception {
        OrderFood orderFood = new OrderFood();
        orderFood.setOrder(order);
        orderFood.setFood(food);
        orderFood.setOrderQuantity(quantity);
        orderFoodDao.save(orderFood);
    }

    private void reduceStock(Food food, int orderedQty) throws Exception {
        int available = Integer.parseInt(food.getQuantity());
        int newQty = available - orderedQty;
        if (newQty < 0) {
            newQty = 0;
        }
        food.setQuantity(String.valueOf(newQty));
        foodDao.update(food);
    }

    @Override
    public List<CartTm> getAllOrders() {
        List<OrderFood> allOrderFoods = orderFoodDao.getAll();
        List<CartTm> cartList = new ArrayList<>();

        for (OrderFood of : allOrderFoods) {
            CartTm tm = new CartTm(
                    (int) of.getFood().getFoodId(),
                    of.getFood().getName(),
                    of.getOrderQuantity(),
                    of.getFood().getUnitPrice(),
                    String.valueOf(Double.parseDouble(of.getOrderQuantity()) * Double.parseDouble(of.getFood().getUnitPrice())),
                    null
            );
            cartList.add(tm);
        }

        Map<String, Integer> packageCount = new HashMap<>();
        for (CartTm tm : cartList) {
            if (tm.getName().equalsIgnoreCase("chips") ||
                    tm.getName().equalsIgnoreCase("drink") ||
                    tm.getName().equalsIgnoreCase("pop corn")) {

                String key = "Package";
                int count = packageCount.getOrDefault(key, 0);
                count = Math.min(count, Integer.parseInt(tm.getQuantity()));
                packageCount.put(key, count + Integer.parseInt(tm.getQuantity()));
            }
        }

        for (String key : packageCount.keySet()) {
            CartTm packageTm = new CartTm(
                    0,
                    "Package",
                    String.valueOf(packageCount.get(key)),
                    "0",
                    "0",
                    null
            );
            cartList.add(packageTm);
        }

        return cartList;
    }

    @Override
    public List<OrderFood> getAllOrderFoods() {
        return orderFoodDao.getAll();
    }
    @Override
    public Long getLastOrderId() throws Exception {
        return orderDao.getLastOrderId();
    }

}
