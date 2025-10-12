package lk.ijse.grim_fieldfood_stall.model;

public class FoodTM {
    private long foodId;
    private String name;
    private String quantity;
    private String unitPrice;
    private String totalPrice;

    public FoodTM() {}

    public FoodTM(long foodId, String name, String quantity, String unitPrice, String totalPrice) {
        this.foodId = foodId;
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
    }

    public long getFoodId() { return foodId; }
    public void setFoodId(long foodId) { this.foodId = foodId; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getQuantity() { return quantity; }
    public void setQuantity(String quantity) { this.quantity = quantity; }
    
    public String getUnitPrice() { return unitPrice; }
    public void setUnitPrice(String unitPrice) { this.unitPrice = unitPrice; }
    
    public String getTotalPrice() { return totalPrice; }
    public void setTotalPrice(String totalPrice) { this.totalPrice = totalPrice; }
}
