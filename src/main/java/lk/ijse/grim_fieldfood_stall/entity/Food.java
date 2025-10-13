package lk.ijse.grim_fieldfood_stall.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    @Column(nullable = false)
    private String unitBuyingPrice;

    public Food(long foodId, String name, String quantity, String unitPrice, String totalPrice, String unitBuyingPrice) {
        this.foodId = foodId;
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.unitBuyingPrice = unitBuyingPrice;
    }

    public Food(String name, String quantity, String unitPrice, String totalPrice, String unitBuyingPrice) {
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.unitBuyingPrice = unitBuyingPrice;
    }

    @OneToMany(mappedBy = "food")
    private List<OrderFood> orderFoods;


}
