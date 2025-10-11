package lk.ijse.grim_fieldfood_stall.bo.custom;

import lk.ijse.grim_fieldfood_stall.bo.SuperBO;
import lk.ijse.grim_fieldfood_stall.dao.SuperDao;
import lk.ijse.grim_fieldfood_stall.dto.OrderDto;

public interface OrderBo extends SuperBO {
    boolean placeOrder(OrderDto dto) throws Exception;

    Object getAllOrders();
}
