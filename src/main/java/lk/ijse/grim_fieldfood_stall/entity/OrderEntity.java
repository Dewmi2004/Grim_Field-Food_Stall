package lk.ijse.grim_fieldfood_stall.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;
    @Column(nullable = false)
    private String date;
    @Column(nullable = false)
    private String totalAmount;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderFood> orderFoods = new ArrayList<>();

    public OrderEntity() {}

    public OrderEntity(String date, String totalAmount) {
        this.date = date;
        this.totalAmount = totalAmount;
    }

    public long getOrderId() { return orderId; }
    public void setOrderId(long orderId) { this.orderId = orderId; }
    
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    
    public String getTotalAmount() { return totalAmount; }
    public void setTotalAmount(String totalAmount) { this.totalAmount = totalAmount; }
    
    public List<OrderFood> getOrderFoods() { return orderFoods; }
    public void setOrderFoods(List<OrderFood> orderFoods) { this.orderFoods = orderFoods; }
}
