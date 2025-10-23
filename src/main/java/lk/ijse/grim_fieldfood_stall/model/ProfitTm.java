package lk.ijse.grim_fieldfood_stall.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfitTm {
    private long orderId;
    private int quantity;
    private double profit;


}
