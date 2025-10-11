package lk.ijse.grim_fieldfood_stall.dto;

import javafx.collections.ObservableList;
import lk.ijse.grim_fieldfood_stall.model.CartTm;
import lombok.*;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long orderId;
    private String date;
    private String totalAmount;
    private List<CartTm> cartList;

    public OrderDto(String date, String totalAmount, ObservableList<CartTm> cartList) {
        this.date = date;
        this.totalAmount = totalAmount;
        this.cartList = cartList;
    }
}