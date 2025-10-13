package lk.ijse.grim_fieldfood_stall.bo.custom.impl;

import lk.ijse.grim_fieldfood_stall.bo.custom.FoodBO;
import lk.ijse.grim_fieldfood_stall.dao.DaoFactory;
import lk.ijse.grim_fieldfood_stall.dao.custom.FoodDao;
import lk.ijse.grim_fieldfood_stall.dto.FoodDto;
import lk.ijse.grim_fieldfood_stall.entity.Food;
import java.util.ArrayList;
import java.util.List;

public class FoodBOImpl implements FoodBO {
    FoodDao foodDAO = (FoodDao) DaoFactory.getInstance().getDAO(DaoFactory.DAOtypes.FOOD);

    @Override
    public boolean saveFood(FoodDto dto) {
        return foodDAO.save(new Food( dto.getName(), dto.getQuantity(), dto.getUnitPrice(), dto.getTotalPrice(),dto.getUnitBuyingPrice()));
    }

    @Override
    public boolean updateFood(FoodDto dto) {
        return foodDAO.update(new Food(dto.getFoodId(), dto.getName(), dto.getQuantity(), dto.getUnitPrice(), dto.getTotalPrice(), dto.getUnitBuyingPrice()));
    }

    @Override
    public boolean deleteFood(String id) {
        return foodDAO.delete(id);
    }

    @Override
    public FoodDto getFood(String id) {
        Food f = foodDAO.get(id);
        return f != null ? new FoodDto(f.getFoodId(), f.getName(), f.getQuantity(), f.getUnitPrice(), f.getTotalPrice(),f.getUnitBuyingPrice()) : null;
    }

    @Override
    public List<FoodDto> getAllFoods() {
        List<FoodDto> list = new ArrayList<>();
        for (Food f : foodDAO.getAll()) {
            list.add(new FoodDto(f.getFoodId(), f.getName(), f.getQuantity(), f.getUnitPrice(), f.getTotalPrice(),f.getUnitBuyingPrice()));
        }
        return list;
    }

    @Override
    public Food findByName(String itemName) {
        return foodDAO.findByName(itemName);
    }


}
