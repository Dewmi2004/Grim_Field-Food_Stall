package lk.ijse.grim_fieldfood_stall.bo;


import lk.ijse.grim_fieldfood_stall.bo.custom.impl.FoodBOImpl;

public class BOFactory {
    private static BOFactory instance;

    private BOFactory() {}

    public static BOFactory getInstance(){
        if(instance==null){
            instance=new BOFactory();

        }
        return instance;
    }
    public enum BOtypes{
        FOOD
    }
    public SuperBO getBO(BOtypes dao){
        switch(dao){
            case FOOD:
                return new FoodBOImpl();

            default:
                return null;
        }
    }
}
