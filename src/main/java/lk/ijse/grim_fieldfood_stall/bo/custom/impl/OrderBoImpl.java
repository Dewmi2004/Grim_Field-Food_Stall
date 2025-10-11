package lk.ijse.grim_fieldfood_stall.bo.custom.impl;

import lk.ijse.grim_fieldfood_stall.bo.custom.OrderBo;
import lk.ijse.grim_fieldfood_stall.dao.DaoFactory;
import lk.ijse.grim_fieldfood_stall.dao.custom.FoodDao;
import lk.ijse.grim_fieldfood_stall.dao.custom.OrderDao;
import lk.ijse.grim_fieldfood_stall.dto.OrderDto;
import lk.ijse.grim_fieldfood_stall.entity.Food;
import lk.ijse.grim_fieldfood_stall.entity.OrderEntity;
import lk.ijse.grim_fieldfood_stall.model.CartTm;

public class OrderBoImpl implements OrderBo {
    private final OrderDao orderDao = (OrderDao) DaoFactory.getInstance().getDAO(DaoFactory.DAOtypes.ORDER);
    private final FoodDao foodDao = (FoodDao) DaoFactory.getInstance().getDAO(DaoFactory.DAOtypes.FOOD);
    @Override
    public boolean placeOrder(OrderDto dto) throws Exception {
        boolean saved = orderDao.save(new OrderEntity( dto.getDate(), dto.getTotalAmount()));

        if (!saved) return false;

        for (CartTm item : dto.getCartList()) {
            Food entity = foodDao.findByName(item.getName());
            if (entity == null) continue;

            int available = Integer.parseInt(entity.getQuantity());
            int ordered = Integer.parseInt(item.getQuantity());
            entity.setQuantity(String.valueOf(available - ordered));
            foodDao.update(entity);
        }

        return true;
    }
}
