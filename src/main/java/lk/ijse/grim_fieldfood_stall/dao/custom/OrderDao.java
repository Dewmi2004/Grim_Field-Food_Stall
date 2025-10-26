package lk.ijse.grim_fieldfood_stall.dao.custom;

import lk.ijse.grim_fieldfood_stall.dao.SuperDao;
import lk.ijse.grim_fieldfood_stall.entity.OrderEntity;

import java.util.List;

public interface OrderDao extends SuperDao {
    boolean save(OrderEntity entity) throws Exception;

    List<OrderEntity> getAll();

    Long getLastOrderId();
}
