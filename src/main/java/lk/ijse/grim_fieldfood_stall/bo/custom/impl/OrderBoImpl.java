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
import java.util.List;

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
            Food foodEntity = foodDao.findById(item.getFoodId());
            if (foodEntity == null) continue;

            OrderFood orderFood = new OrderFood();
            orderFood.setOrder(order);

            Food foodRef = new Food();
            foodRef.setFoodId(item.getFoodId());
            orderFood.setFood(foodRef);

            orderFood.setOrderQuantity(item.getQuantity());
            orderFoodDao.save(orderFood);

//            int available = Integer.parseInt(entity.getQuantity());
//            int ordered = Integer.parseInt(item.getQuantity());
//            entity.setQuantity(String.valueOf(available - ordered));
//            foodDao.update(entity);
        }

        return true;
    }

    @Override
    public Object getAllOrders() {
        List<OrderDto> list = new ArrayList<>();
        for (OrderEntity f : orderDao.getAll()) {
            list.add(new OrderDto(f.getOrderId (),f.getDate(),f.getTotalAmount()));
        }
        return list;
    }
}
