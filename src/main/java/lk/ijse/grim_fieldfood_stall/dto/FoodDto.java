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


    public FoodDto(String name, String quantity, String unitPrice) {
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;

    }
}
