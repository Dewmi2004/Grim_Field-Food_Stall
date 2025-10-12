package lk.ijse.grim_fieldfood_stall.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "order_food")
public class OrderFood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;

    @Column(name = "order_quantity", nullable = false)
    private String orderQuantity;

    public OrderFood() {}

    public OrderFood(long id, OrderEntity order, Food food, String orderQuantity) {
        this.id = id;
        this.order = order;
        this.food = food;
        this.orderQuantity = orderQuantity;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    
    public OrderEntity getOrder() { return order; }
    public void setOrder(OrderEntity order) { this.order = order; }
    
    public Food getFood() { return food; }
    public void setFood(Food food) { this.food = food; }
    
    public String getOrderQuantity() { return orderQuantity; }
    public void setOrderQuantity(String orderQuantity) { this.orderQuantity = orderQuantity; }
}
