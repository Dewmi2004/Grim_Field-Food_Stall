package lk.ijse.grim_fieldfood_stall.bo.custom.impl;

import lk.ijse.grim_fieldfood_stall.bo.custom.FoodBO;
import lk.ijse.grim_fieldfood_stall.dao.DaoFactory;
import lk.ijse.grim_fieldfood_stall.dao.custom.FoodDao;
import lk.ijse.grim_fieldfood_stall.dto.FoodDTO;
import lk.ijse.grim_fieldfood_stall.entity.Food;
import java.util.ArrayList;
import java.util.List;

public class FoodBOImpl implements FoodBO {
    FoodDao foodDAO = (FoodDao) DaoFactory.getInstance().getDAO(DaoFactory.DAOtypes.FOOD);

    @Override
    public boolean saveFood(FoodDTO dto) {
        return foodDAO.save(new Food( dto.getName(), dto.getQuantity(), dto.getUnitPrice(), dto.getTotalPrice()));
    }

    @Override
    public boolean updateFood(FoodDTO dto) {
        return foodDAO.update(new Food(dto.getFoodId(), dto.getName(), dto.getQuantity(), dto.getUnitPrice(), dto.getTotalPrice()));
    }

    @Override
    public boolean deleteFood(String id) {
        return foodDAO.delete(id);
    }

    @Override
    public FoodDTO getFood(String id) {
        Food f = foodDAO.get(id);
        return f != null ? new FoodDTO(f.getFoodId(), f.getName(), f.getQuantity(), f.getUnitPrice(), f.getTotalPrice()) : null;
    }

    @Override
    public List<FoodDTO> getAllFoods() {
        List<FoodDTO> list = new ArrayList<>();
        for (Food f : foodDAO.getAll()) {
            list.add(new FoodDTO(f.getFoodId(), f.getName(), f.getQuantity(), f.getUnitPrice(), f.getTotalPrice()));
        }
        return list;
    }
}
