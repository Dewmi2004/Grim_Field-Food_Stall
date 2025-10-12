package lk.ijse.grim_fieldfood_stall.dto;

import javafx.collections.ObservableList;
import lk.ijse.grim_fieldfood_stall.model.CartTm;
import java.util.List;

public class OrderDto {
    private Long orderId;
    private String date;
    private String totalAmount;
    private List<CartTm> cartList;

    public OrderDto() {}

    public OrderDto(Long orderId, String date, String totalAmount) {
        this.orderId = orderId;
        this.date = date;
        this.totalAmount = totalAmount;
    }

    public OrderDto(String date, String totalAmount, ObservableList<CartTm> cartList) {
        this.date = date;
        this.totalAmount = totalAmount;
        this.cartList = cartList;
    }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    
    public String getTotalAmount() { return totalAmount; }
    public void setTotalAmount(String totalAmount) { this.totalAmount = totalAmount; }
    
    public List<CartTm> getCartList() { return cartList; }
    public void setCartList(List<CartTm> cartList) { this.cartList = cartList; }
}