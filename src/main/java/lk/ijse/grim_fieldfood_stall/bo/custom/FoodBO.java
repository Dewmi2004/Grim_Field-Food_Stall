package lk.ijse.grim_fieldfood_stall.bo.custom;

import lk.ijse.grim_fieldfood_stall.bo.SuperBO;
import lk.ijse.grim_fieldfood_stall.dto.FoodDTO;
import java.util.List;

public interface FoodBO extends SuperBO {
    boolean saveFood(FoodDTO dto);
    boolean updateFood(FoodDTO dto);
    boolean deleteFood(String id);
    FoodDTO getFood(String id);
    List<FoodDTO> getAllFoods();
}
