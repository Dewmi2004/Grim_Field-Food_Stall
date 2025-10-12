package lk.ijse.grim_fieldfood_stall.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfitTm {
    private String foodId;
    private String name;
    private int qty;
    private double unitPrice;
    private double totalPrice;
    private double profit;
}
