package lk.ijse.grim_fieldfood_stall.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
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
import java.util.Optional;

public class OrderPageController implements Initializable {

    public Label lblUnitPrice;

    @FXML
    private AnchorPane orderAnchorPane;

    @FXML
    private Button btnAddToCart;

    @FXML
    private Button btnCheckBalance;

    @FXML
    private Button btnPlaceOrder;

    @FXML
    private Button btnGoBack;

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
            for (FoodDto i : list) {
                items.add(i.getName());
            }
            cmbItemId.setItems(items);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load food items: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    private void setTableColumns() {
        colItemId.setCellValueFactory(new PropertyValueFactory<>("foodId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colRemove.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));
        tblCart.setItems(cartList);
    }

    @FXML
    void btnAddtoCartOnAction(ActionEvent event) {
        try {
            String itemName = cmbItemId.getValue();

            if (itemName == null || itemName.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please select an item!").show();
                return;
            }

            if (txtQuantity.getText().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please enter quantity!").show();
                return;
            }

            Food entity = foodBO.findByName(itemName);
            if (entity == null) {
                new Alert(Alert.AlertType.ERROR, "Item not found!").show();
                return;
            }

            int available = Integer.parseInt(entity.getQuantity());
            int qty = Integer.parseInt(txtQuantity.getText());

            if (qty <= 0) {
                new Alert(Alert.AlertType.WARNING, "Quantity must be greater than 0!").show();
                return;
            }

            if (available <= 0) {
                new Alert(Alert.AlertType.WARNING, "No quantity left for this item!").show();
                return;
            }

            if (qty > available) {
                new Alert(Alert.AlertType.WARNING, "Requested quantity exceeds available! Available: " + available).show();
                return;
            }

            Optional<CartTm> existingItem = cartList.stream()
                    .filter(tm -> tm.getFoodId() == entity.getFoodId())
                    .findFirst();

            if (existingItem.isPresent()) {
                new Alert(Alert.AlertType.WARNING, "Item already in cart! Remove it first to update quantity.").show();
                return;
            }

            double unitPrice = Double.parseDouble(entity.getUnitPrice());
            double total = qty * unitPrice;

            Button btnRemove = new Button("Remove");
            btnRemove.setStyle("-fx-background-color: #ff0000; -fx-text-fill: white;");

            CartTm tm = new CartTm(
                    entity.getFoodId(),
                    entity.getName(),
                    String.valueOf(qty),
                    entity.getUnitPrice(),
                    String.format("%.2f", total),
                    btnRemove
            );

            btnRemove.setOnAction(e -> {
                cartList.remove(tm);
                calculateTotal();
            });

            cartList.add(tm);
            tblCart.refresh();
            calculateTotal();

            txtQuantity.clear();
            cmbItemId.setValue(null);

        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid number for quantity!").show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error adding item to cart: " + e.getMessage()).show();
            e.printStackTrace();
        }
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
        try {
            if (txtPaidAmount.getText().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please enter paid amount!").show();
                return;
            }

            double paid = Double.parseDouble(txtPaidAmount.getText());
            double total = Double.parseDouble(lblTotalAmount.getText());

            if (paid < total) {
                new Alert(Alert.AlertType.WARNING, "Insufficient payment! Need: " + String.format("%.2f", total - paid) + " more").show();
                lblChange.setText("0.00");
                return;
            }

            double change = paid - total;
            lblChange.setText(String.format("%.2f", change));

        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid amount!").show();
        }
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        try {
            if (cartList.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Cart is empty! Add items before placing order.").show();
                return;
            }

            if (txtPaidAmount.getText().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please enter paid amount!").show();
                return;
            }

            double paid = Double.parseDouble(txtPaidAmount.getText());
            double total = Double.parseDouble(lblTotalAmount.getText());

            if (paid < total) {
                new Alert(Alert.AlertType.WARNING, "Insufficient payment!").show();
                return;
            }

            OrderDto dto = new OrderDto(txtDate.getText(), lblTotalAmount.getText(), cartList);
            boolean success = orderBo.placeOrder(dto);

            if (success) {
                new Alert(Alert.AlertType.INFORMATION, "Order placed successfully!\nChange: " + lblChange.getText()).show();
                clearAll();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to place order!").show();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid amount!").show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error placing order: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    @FXML
    void btnGoBackOnAction(ActionEvent event) {
        try {
            AnchorPane parentPane = (AnchorPane) orderAnchorPane.getParent();
            parentPane.getChildren().clear();

            AnchorPane dashboardPane = FXMLLoader.load(getClass().getResource("/lk/ijse/grim_fieldfood_stall/assests/DashBoard.fxml"));

            dashboardPane.prefWidthProperty().bind(parentPane.widthProperty());
            dashboardPane.prefHeightProperty().bind(parentPane.heightProperty());

            parentPane.getChildren().add(dashboardPane);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to navigate to Dashboard: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    private void clearAll() {
        cartList.clear();
        lblTotalAmount.setText("0.00");
        txtPaidAmount.clear();
        lblChange.setText("0.00");
        cmbItemId.setValue(null);
        txtQuantity.clear();
        lblItemId.setText("");
        lblAvailableQuantity.setText("");
        lblUnitPrice.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtDate.setText(String.valueOf(LocalDate.now()));
        loadItemNames();
        setTableColumns();
        lblTotalAmount.setText("0.00");
        lblChange.setText("0.00");
    }

    @FXML
    public void ItemInfoOnAction(ActionEvent actionEvent) {
        try {
            String itemName = cmbItemId.getValue();
            if (itemName != null && !itemName.isEmpty()) {
                Food entity = foodBO.findByName(itemName);
                if (entity != null) {
                    lblItemId.setText(String.valueOf(entity.getFoodId()));
                    lblAvailableQuantity.setText(entity.getQuantity());
                    lblUnitPrice.setText(entity.getUnitPrice());
                }
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error loading item info: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }
}