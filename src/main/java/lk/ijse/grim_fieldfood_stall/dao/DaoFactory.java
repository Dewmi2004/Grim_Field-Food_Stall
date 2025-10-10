package lk.ijse.grim_fieldfood_stall.dao;

import lk.ijse.grim_fieldfood_stall.dao.custom.impl.FoodDaoImpl;


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
        FOOD
    }
    public SuperDao getDAO(DAOtypes dao){
        switch(dao){
            case FOOD:
                return new FoodDaoImpl();

            default:
                return null;
        }
    }
}
