package lk.ijse.grim_fieldfood_stall.bo;


import lk.ijse.grim_fieldfood_stall.bo.custom.impl.FoodBOImpl;
import lk.ijse.grim_fieldfood_stall.bo.custom.impl.OrderBoImpl;

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
        FOOD,ORDER
    }
    public SuperBO getBO(BOtypes dao){
        switch(dao){
            case FOOD:
                return new FoodBOImpl();
                case ORDER:
                    return new OrderBoImpl();

            default:
                return null;
        }
    }
}
