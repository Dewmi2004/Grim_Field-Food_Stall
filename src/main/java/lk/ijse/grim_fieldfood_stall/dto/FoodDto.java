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
    private String unitBuyingPrice;

    public FoodDto(String name, String quantity, String unitPrice, String totalPrice, String unitBuyingPrice) {
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.unitBuyingPrice = unitBuyingPrice;
    }
}
