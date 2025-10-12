package lk.ijse.grim_fieldfood_stall.bo.custom;

import lk.ijse.grim_fieldfood_stall.bo.SuperBO;
import lk.ijse.grim_fieldfood_stall.dao.SuperDao;
import lk.ijse.grim_fieldfood_stall.dto.OrderDto;
import lk.ijse.grim_fieldfood_stall.entity.OrderFood;

import java.util.List;

public interface OrderBo extends SuperBO {
    boolean placeOrder(OrderDto dto) throws Exception;

    Object getAllOrders();

    List<OrderFood> getAllOrderFoods();
}
