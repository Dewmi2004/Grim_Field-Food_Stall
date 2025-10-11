package lk.ijse.grim_fieldfood_stall.dao.custom;

import lk.ijse.grim_fieldfood_stall.dao.SuperDao;
import lk.ijse.grim_fieldfood_stall.entity.OrderFood;

public interface OrderFoodDao extends SuperDao {
    boolean save(OrderFood entity) throws Exception;

}
