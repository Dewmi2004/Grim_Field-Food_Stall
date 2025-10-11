package lk.ijse.grim_fieldfood_stall.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    public OrderEntity(String date, String totalAmount) {
        this.date = date;
        this.totalAmount = totalAmount;
    }
}
