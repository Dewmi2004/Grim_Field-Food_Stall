package lk.ijse.grim_fieldfood_stall.dao;

import lk.ijse.grim_fieldfood_stall.dao.custom.impl.FoodDaoImpl;
import lk.ijse.grim_fieldfood_stall.dao.custom.impl.OrderDaoImpl;


public class DaoFactory {
    private static DaoFactory instance;

    private DaoFactory() {}

    public static DaoFactory getInstance(){
        if(instance==null){
            instance=new DaoFactory();

        }
        return instance;
    }
    public enum DAOtypes{
        FOOD,ORDER
    }
    public SuperDao getDAO(DAOtypes dao){
        switch(dao){
            case FOOD:
                return new FoodDaoImpl();
                case ORDER:
                    return new OrderDaoImpl();

            default:
                return null;
        }
    }
}
