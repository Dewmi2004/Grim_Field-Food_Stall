package lk.ijse.grim_fieldfood_stall.bo.custom;

import lk.ijse.grim_fieldfood_stall.bo.SuperBO;
import lk.ijse.grim_fieldfood_stall.dto.FoodDto;
import lk.ijse.grim_fieldfood_stall.entity.Food;

import java.util.ArrayList;
import java.util.List;

public interface FoodBO extends SuperBO {
    boolean saveFood(FoodDto dto);
    boolean updateFood(FoodDto dto);
    boolean deleteFood(String id);
    FoodDto getFood(String id);
    List<FoodDto> getAllFoods();

    Food findByName(String itemName);
}
