package lk.ijse.grim_fieldfood_stall.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "food")
public class Food {
    @Id
    @Column(name = "food_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long foodId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String quantity;
    @Column(nullable = false)
    private String unitPrice;
    @Column(nullable = false)
    private String totalPrice;

    @OneToMany(mappedBy = "food")
    private List<OrderFood> orderFoods;
    
    public Food() {}
    
    public Food(String name, String quantity, String unitPrice, String totalPrice) {
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
    }

    public Food(long foodId, String name, String quantity, String unitPrice, String totalPrice) {
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
    
    public List<OrderFood> getOrderFoods() { return orderFoods; }
    public void setOrderFoods(List<OrderFood> orderFoods) { this.orderFoods = orderFoods; }
}
