package lk.ijse.grim_fieldfood_stall.entity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_food")
public class OrderFood {


    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;

    @Column(name = "order_quantity", nullable = false)
    private String orderQuantity;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

}
