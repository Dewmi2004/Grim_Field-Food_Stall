package lk.ijse.grim_fieldfood_stall.dao.custom;

import lk.ijse.grim_fieldfood_stall.dao.SuperDao;
import lk.ijse.grim_fieldfood_stall.entity.OrderEntity;
import lk.ijse.grim_fieldfood_stall.entity.OrderFood;

import java.util.List;

public interface OrderFoodDao extends SuperDao {
    boolean save(OrderFood entity) throws Exception;
    List<OrderFood> getAll();

}
