package lk.ijse.grim_fieldfood_stall.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lk.ijse.grim_fieldfood_stall.bo.BOFactory;
import lk.ijse.grim_fieldfood_stall.bo.custom.FoodBO;
import lk.ijse.grim_fieldfood_stall.bo.custom.OrderBo;
import lk.ijse.grim_fieldfood_stall.dto.FoodDto;
import lk.ijse.grim_fieldfood_stall.dto.OrderDto;
import lk.ijse.grim_fieldfood_stall.model.CartTm;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class OrderPageController implements Initializable {

    public ImageView imgDrink, imgCorn, imgChips;

    @FXML
    private Label lblOrderId, lblTotalAmount, lblChange, lblPriceChips, lblQtyChips,
            lblPriceDrink, lblQtyDrink, lblPriceCorn, lblQtyCorn, lblQtyAll;

    @FXML
    private Button btnBack, btnCheckBalance, btnPlaceOrder;

    @FXML
    private TableView<CartTm> tblCart;

    @FXML
    private TableColumn<?, ?> colItemId, colName, colQty, colUnitPrice, colTotal, colRemove;

    @FXML
    private TextField txtPaidAmount, txtDate;

    @FXML
    private VBox rootVBox;

    private final ObservableList<CartTm> cartList = FXCollections.observableArrayList();
    private final FoodBO foodBO = (FoodBO) BOFactory.getInstance().getBO(BOFactory.BOtypes.FOOD);
    private final OrderBo orderBo = (OrderBo) BOFactory.getInstance().getBO(BOFactory.BOtypes.ORDER);

    private final Map<String, FoodDto> foodMap = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtDate.setText(LocalDate.now().toString());
        lblOrderId.setText("(Auto-generated)");
        setTableColumns();
        loadFoodItems();

        rootVBox.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyPressed(this::rootOnKeyPressed);
            }
        });
    }

    private void rootOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            btnPlaceOrderOnAction(null);
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

    private void loadFoodItems() {
        try {
            List<FoodDto> list = foodBO.getAllFoods();
            for (FoodDto food : list) {
                foodMap.put(food.getName().toLowerCase(), food);
            }

            if (foodMap.containsKey("chips")) lblPriceChips.setText(foodMap.get("chips").getUnitPrice());
            if (foodMap.containsKey("cola")) lblPriceDrink.setText(foodMap.get("cola").getUnitPrice());
            if (foodMap.containsKey("pop corn")) lblPriceCorn.setText(foodMap.get("pop corn").getUnitPrice());
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load food items!").show();
        }
    }

    private void addToCart(FoodDto food, int qty) {
        if (food == null) {
            new Alert(Alert.AlertType.WARNING, "Food item not found!").show();
            return;
        }

        int available = Integer.parseInt(food.getQuantity());
        if (qty <= 0 || qty > available) {
            new Alert(Alert.AlertType.WARNING, "Invalid quantity for " + food.getName()).show();
            return;
        }

        double unitPrice = Double.parseDouble(food.getUnitPrice());
        double total = qty * unitPrice;

        for (CartTm tm : cartList) {
            if (tm.getFoodId() == food.getFoodId()) {
                new Alert(Alert.AlertType.WARNING, "Item already in cart!").show();
                return;
            }
        }

        Button btnRemove = new Button("Remove");
        CartTm tm = new CartTm(
                food.getFoodId(),
                food.getName(),
                String.valueOf(qty),
                food.getUnitPrice(),
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
    }

    private void calculateTotal() {
        double total = 0;
        for (CartTm tm : cartList) {
            total += Double.parseDouble(tm.getTotal());
        }
        lblTotalAmount.setText(String.format("%.2f", total));
    }

    private void adjustQty(Label label, int delta) {
        int qty = Integer.parseInt(label.getText());
        int newQty = Math.max(0, qty + delta);
        label.setText(String.valueOf(newQty));
    }

    @FXML
    void btnMinusChipsOnAction(ActionEvent event) { adjustQty(lblQtyChips, -1); }
    @FXML
    void btnPlusChipsOnAction(ActionEvent event) { adjustQty(lblQtyChips, +1); }
    @FXML
    void btnMinusDrinkOnAction(ActionEvent event) { adjustQty(lblQtyDrink, -1); }
    @FXML
    void btnPlusDrinkOnAction(ActionEvent event) { adjustQty(lblQtyDrink, +1); }
    @FXML
    void btnMinusCornOnAction(ActionEvent event) { adjustQty(lblQtyCorn, -1); }
    @FXML
    void btnPlusCornOnAction(ActionEvent event) { adjustQty(lblQtyCorn, +1); }
    @FXML
    void btnMinusAllOnAction(ActionEvent event) { adjustQty(lblQtyAll, -1); }
    @FXML
    void btnPlusAllOnAction(ActionEvent event) { adjustQty(lblQtyAll, +1); }

    @FXML
    void btnAddChipsOnAction(ActionEvent event) { addToCart(foodMap.get("chips"), Integer.parseInt(lblQtyChips.getText())); }
    @FXML
    void btnAddDrinkOnAction(ActionEvent event) { addToCart(foodMap.get("cola"), Integer.parseInt(lblQtyDrink.getText())); }
    @FXML
    void btnAddCornOnAction(ActionEvent event) { addToCart(foodMap.get("pop corn"), Integer.parseInt(lblQtyCorn.getText())); }

    @FXML
    void btnAddAllOnAction(ActionEvent event) {
        int packageQty = Integer.parseInt(lblQtyAll.getText());
        if (packageQty <= 0) {
            new Alert(Alert.AlertType.WARNING, "Package quantity must be at least 1!").show();
            return;
        }

        cartList.removeIf(tm -> tm.getName().equalsIgnoreCase("Package"));

        double chipsPrice = Double.parseDouble(foodMap.get("chips").getUnitPrice());
        double drinkPrice = Double.parseDouble(foodMap.get("cola").getUnitPrice()) - 50; // discount
        if (drinkPrice < 0) drinkPrice = 0;
        double cornPrice = Double.parseDouble(foodMap.get("pop corn").getUnitPrice());

        double packagePricePerUnit = chipsPrice + drinkPrice + cornPrice;
        double total = packageQty * packagePricePerUnit;

        Button btnRemove = new Button("Remove");
        CartTm packageItem = new CartTm(
                0,
                "Package",
                String.valueOf(packageQty),
                String.format("%.2f", packagePricePerUnit),
                String.format("%.2f", total),
                btnRemove
        );

        btnRemove.setOnAction(e -> {
            cartList.remove(packageItem);
            tblCart.refresh();
            calculateTotal();
        });

        cartList.add(packageItem);
        tblCart.refresh();
        calculateTotal();
    }

    @FXML
    void btnCheckBalanceOnAction(ActionEvent event) {
        String paidText = txtPaidAmount.getText().trim();
        if (!paidText.matches("\\d+(\\.\\d{1,2})?")) {
            new Alert(Alert.AlertType.WARNING, "Paid amount must be a valid number!").show();
            return;
        }

        double paid = Double.parseDouble(paidText);
        double total = Double.parseDouble(lblTotalAmount.getText());
        double change = paid - total;
        lblChange.setText(String.format("%.2f", change));
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        if (cartList.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Cart is empty!").show();
            return;
        }

        try {
            ObservableList<CartTm> items = FXCollections.observableArrayList(cartList);

            for (CartTm tm : items) {
                if (!tm.getName().equalsIgnoreCase("Package")) {
                    FoodDto food = foodMap.values().stream().filter(f -> f.getFoodId() == tm.getFoodId()).findFirst().orElse(null);
                    if (food != null) {
                        food.setQuantity(String.valueOf(Integer.parseInt(food.getQuantity()) - Integer.parseInt(tm.getQuantity())));
                    }
                }
            }

            OrderDto orderDto = new OrderDto(
                    txtDate.getText(),
                    lblTotalAmount.getText(),
                    items
            );

            boolean success = orderBo.placeOrder(orderDto);
            if (success) {
                new Alert(Alert.AlertType.INFORMATION, "🎃 Order placed successfully! 👻").show();
                clearAll();
                lblOrderId.setText("(Auto-generated)");
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to place order!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error placing order!").show();
        }
    }

    private void clearAll() {
        cartList.clear();
        tblCart.refresh();
        lblTotalAmount.setText("0.00");
        lblChange.setText("0.00");
        txtPaidAmount.clear();
        lblQtyChips.setText("0");
        lblQtyDrink.setText("0");
        lblQtyCorn.setText("0");
        lblQtyAll.setText("0");
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/lk/ijse/grim_fieldfood_stall/assests/DashBoard.fxml"));
        Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.setTitle("Dashboard");
        stage.show();
    }
}
