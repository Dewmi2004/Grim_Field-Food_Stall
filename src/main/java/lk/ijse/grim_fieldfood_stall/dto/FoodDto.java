package lk.ijse.grim_fieldfood_stall.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class FoodDto {
    private long foodId;
    private String name;
    private String quantity;
    private String unitPrice;
    private String totalPrice;

    public FoodDto(String name, String quantity, String unitPrice, String totalPrice) {
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
    }
}
