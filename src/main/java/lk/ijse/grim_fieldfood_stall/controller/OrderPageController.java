package lk.ijse.grim_fieldfood_stall.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.grim_fieldfood_stall.bo.BOFactory;
import lk.ijse.grim_fieldfood_stall.bo.custom.FoodBO;
import lk.ijse.grim_fieldfood_stall.bo.custom.OrderBo;
import lk.ijse.grim_fieldfood_stall.dto.FoodDto;
import lk.ijse.grim_fieldfood_stall.dto.OrderDto;
import lk.ijse.grim_fieldfood_stall.entity.Food;
import lk.ijse.grim_fieldfood_stall.model.CartTm;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class OrderPageController implements Initializable {

    public Label lblUnitPrice;
    @FXML
    private Button btnAddToCart;

    @FXML
    private Button btnCheckBalance;

    @FXML
    private Button btnPlaceOrder;

    @FXML
    private ComboBox<String> cmbItemId;

    @FXML
    private TableColumn<?, ?> colItemId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colRemove;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private Label lblAvailableQuantity;

    @FXML
    private Label lblChange;

    @FXML
    private Label lblItemId;

    @FXML
    private Label lblOrderId;


    @FXML
    private Label lblTotalAmount;

    @FXML
    private TableView<CartTm> tblCart;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtPaidAmount;

    @FXML
    private TextField txtQuantity;
    private final ObservableList<CartTm> cartList = FXCollections.observableArrayList();
    private final FoodBO foodBO = (FoodBO) BOFactory.getInstance().getBO(BOFactory.BOtypes.FOOD);
    private final OrderBo orderBo = (OrderBo) BOFactory.getInstance().getBO(BOFactory.BOtypes.ORDER);

    private void loadItemNames() {
        try {
            List<FoodDto> list = foodBO.getAllFoods();
            ObservableList<String> items = FXCollections.observableArrayList();
            for (FoodDto i : list) items.add(i.getName());
            cmbItemId.setItems(items);
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void setTableColumns() {
        colItemId.setCellValueFactory(new PropertyValueFactory<>("foodId"));
        colName.setCellValueFactory(new  PropertyValueFactory<>("name"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colUnitPrice.setCellValueFactory(new  PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colRemove.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));
        tblCart.setItems(cartList);
    }

    @FXML
    void btnAddtoCartOnAction(ActionEvent event) {
        try {
            String itemName = cmbItemId.getValue();
            Food entity = foodBO.findByName(itemName);
            if (entity == null) return;

            int available = Integer.parseInt(entity.getQuantity());
            int qty = Integer.parseInt(txtQuantity.getText());

            if (available <= 0) {
                new Alert(Alert.AlertType.WARNING, "No quantity left for this item!").show();
                return;
            }
            if (qty > available) {
                new Alert(Alert.AlertType.WARNING, "Requested quantity exceeds available!").show();
                return;
            }

            double unitPrice = Double.parseDouble(entity.getUnitPrice());
            double total = qty * unitPrice;

            Button btnRemove = new Button("Remove");
            CartTm tm = new CartTm(entity.getFoodId(), entity.getName(), String.valueOf(qty),
                    entity.getUnitPrice(), String.valueOf(total), btnRemove);

            btnRemove.setOnAction(e -> {
                cartList.remove(tm);
                calculateTotal();
            });

            cartList.add(tm);
            tblCart.refresh();
            calculateTotal();

        } catch (Exception e) { e.printStackTrace(); }
    }

    private void calculateTotal() {
        double total = 0;
        for (CartTm tm : cartList) {
            total += Double.parseDouble(tm.getTotal());
        }
        lblTotalAmount.setText(String.format("%.2f", total));
    }

    @FXML
    void btnCheckBalanceOnAction(ActionEvent event) {
        double paid = Double.parseDouble(txtPaidAmount.getText());
        double total = Double.parseDouble(lblTotalAmount.getText());
        double change = paid - total;
        lblChange.setText(String.format("%.2f", change));
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        try {
            OrderDto dto = new OrderDto( txtDate.getText(), lblTotalAmount.getText(), cartList);
            boolean success = orderBo.placeOrder(dto);
            if (success) {
                new Alert(Alert.AlertType.INFORMATION, "Order placed successfully!").show();
                clearAll();
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void clearAll() {
        cartList.clear();
        lblTotalAmount.setText("0.00");
        txtPaidAmount.clear();
        lblChange.setText("0.00");
        cmbItemId.setValue(null);
        txtQuantity.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtDate.setText(String.valueOf(LocalDate.now()));
        loadItemNames();
        setTableColumns();
    }

    public void ItemInfoOnAction(ActionEvent actionEvent) {

    }
}
