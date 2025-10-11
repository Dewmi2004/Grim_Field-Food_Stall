package lk.ijse.grim_fieldfood_stall.model;

import javafx.scene.control.Button;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartTm {
    private long foodId;
    private String name;
    private String quantity;
    private String unitPrice;
    private String total;
    private Button btnRemove;
}