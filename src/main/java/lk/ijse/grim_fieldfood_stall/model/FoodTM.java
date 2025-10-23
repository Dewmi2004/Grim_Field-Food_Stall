package lk.ijse.grim_fieldfood_stall.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class FoodTM {
    private long foodId;
    private String name;
    private String quantity;
    private String unitPrice;

}
