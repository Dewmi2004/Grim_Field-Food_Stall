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
    public Label lblPriceAll;

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
    private String selectedItem = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtDate.setText(LocalDate.now().toString());
        lblOrderId.setText("(Auto-generated)");
        setTableColumns();
        loadFoodItems();

        rootVBox.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyPress);
            }
        });
    }

    private void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case ENTER:
                btnPlaceOrderOnAction(new ActionEvent());
                event.consume();
                break;
            case NUMPAD1:
                // Keypad 1 for chips - select chips
                selectedItem = "chips";
                highlightSelectedItem("chips");
                event.consume();
                break;
            case NUMPAD2:
                // Keypad 2 for drink - select drink
                selectedItem = "drink";
                highlightSelectedItem("drink");
                event.consume();
                break;
            case NUMPAD3:
                // Keypad 3 for corn - select corn
                selectedItem = "corn";
                highlightSelectedItem("corn");
                event.consume();
                break;
            case NUMPAD4:
                // Keypad 4 for package - select package
                selectedItem = "package";
                highlightSelectedItem("package");
                event.consume();
                break;
            case RIGHT:
                // Right arrow to increase quantity of selected item
                if (selectedItem != null) {
                    increaseSelectedItem();
                }
                event.consume();
                break;
            case LEFT:
                // Left arrow to decrease quantity of selected item
                if (selectedItem != null) {
                    decreaseSelectedItem();
                }
                event.consume();
                break;
            case SHIFT:
                // Shift key (under Enter) for add to cart
                btnAddToCartOnAction(new ActionEvent());
                event.consume();
                break;
            case BACK_SPACE:
                // Backspace key for go back
                try {
                    btnBackOnAction(new ActionEvent());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                event.consume();
                break;
        }
    }

    private void highlightSelectedItem(String item) {
        // Remove any existing highlights
        imgChips.setStyle("");
        imgDrink.setStyle("");
        imgCorn.setStyle("");
        lblQtyAll.setStyle("");

        // Apply highlight to selected item
        switch (item) {
            case "chips":
                imgChips.setStyle("-fx-effect: dropshadow(three-pass-box, #00ff00, 10, 0, 0, 0);");
                break;
            case "drink":
                imgDrink.setStyle("-fx-effect: dropshadow(three-pass-box, #00ff00, 10, 0, 0, 0);");
                break;
            case "corn":
                imgCorn.setStyle("-fx-effect: dropshadow(three-pass-box, #00ff00, 10, 0, 0, 0);");
                break;
            case "package":
                lblQtyAll.setStyle("-fx-background-color: #00ff00; -fx-text-fill: black;");
                break;
        }
    }

    private void increaseSelectedItem() {
        switch (selectedItem) {
            case "chips":
                adjustQty(lblQtyChips, +1);
                break;
            case "drink":
                adjustQty(lblQtyDrink, +1);
                break;
            case "corn":
                adjustQty(lblQtyCorn, +1);
                break;
            case "package":
                adjustQty(lblQtyAll, +1);
                break;
        }
    }

    private void decreaseSelectedItem() {
        switch (selectedItem) {
            case "chips":
                adjustQty(lblQtyChips, -1);
                break;
            case "drink":
                adjustQty(lblQtyDrink, -1);
                break;
            case "corn":
                adjustQty(lblQtyCorn, -1);
                break;
            case "package":
                adjustQty(lblQtyAll, -1);
                break;
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
            if (foodMap.containsKey("drink")) lblPriceDrink.setText(foodMap.get("drink").getUnitPrice());
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
        if (qty <= 0) {
            new Alert(Alert.AlertType.WARNING, "Quantity must be greater than 0!").show();
            return;
        }

        if (available < qty) {
            new Alert(Alert.AlertType.WARNING,
                    "Not enough stock for " + food.getName() + ". Available: " + available).show();
            return;
        }

        // Reduce local stock immediately (simulate reserve)
        food.setQuantity(String.valueOf(available - qty));

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
            int current = Integer.parseInt(food.getQuantity());
            food.setQuantity(String.valueOf(current + qty));
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

    @FXML void btnMinusChipsOnAction(ActionEvent e) { adjustQty(lblQtyChips, -1); }
    @FXML void btnPlusChipsOnAction(ActionEvent e) { adjustQty(lblQtyChips, +1); }
    @FXML void btnMinusDrinkOnAction(ActionEvent e) { adjustQty(lblQtyDrink, -1); }
    @FXML void btnPlusDrinkOnAction(ActionEvent e) { adjustQty(lblQtyDrink, +1); }
    @FXML void btnMinusCornOnAction(ActionEvent e) { adjustQty(lblQtyCorn, -1); }
    @FXML void btnPlusCornOnAction(ActionEvent e) { adjustQty(lblQtyCorn, +1); }
    @FXML void btnMinusAllOnAction(ActionEvent e) { adjustQty(lblQtyAll, -1); }
    @FXML void btnPlusAllOnAction(ActionEvent e) { adjustQty(lblQtyAll, +1); }


    @FXML
    void btnAddToCartOnAction(ActionEvent e) {
        try {
            int chipsQty = Integer.parseInt(lblQtyChips.getText());
            int drinkQty = Integer.parseInt(lblQtyDrink.getText());
            int cornQty = Integer.parseInt(lblQtyCorn.getText());
            int packageQty = Integer.parseInt(lblQtyAll.getText());

            if (chipsQty == 0 && drinkQty == 0 && cornQty == 0 && packageQty == 0) {
                new Alert(Alert.AlertType.WARNING, "Please select at least one quantity!").show();
                return;
            }

            FoodDto chips = foodMap.get("chips");
            FoodDto drink = foodMap.get("drink");
            FoodDto corn = foodMap.get("pop corn");

            if (chipsQty > 0) addToCart(chips, chipsQty);
            if (drinkQty > 0) addToCart(drink, drinkQty);
            if (cornQty > 0) addToCart(corn, cornQty);

            if (packageQty > 0) addPackageToCart(packageQty, chips, drink, corn);

            lblQtyChips.setText("0");
            lblQtyDrink.setText("0");
            lblQtyCorn.setText("0");
            lblQtyAll.setText("0");

            // Clear selection after adding to cart
            clearSelection();

        } catch (Exception ex) {
            ex.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error adding items to cart!").show();
        }
    }

    private void clearSelection() {
        selectedItem = null;
        imgChips.setStyle("");
        imgDrink.setStyle("");
        imgCorn.setStyle("");
        lblQtyAll.setStyle("");
    }

    private void addPackageToCart(int packageQty, FoodDto chips, FoodDto drink, FoodDto corn) {
        if (chips == null || drink == null || corn == null) {
            new Alert(Alert.AlertType.ERROR, "One or more package items not found!").show();
            return;
        }

        int chipsQty = Integer.parseInt(chips.getQuantity());
        int drinkQty = Integer.parseInt(drink.getQuantity());
        int cornQty = Integer.parseInt(corn.getQuantity());

        if (chipsQty < packageQty || drinkQty < packageQty || cornQty < packageQty) {
            new Alert(Alert.AlertType.WARNING,
                    "Not enough stock for package!\n" +
                            "Chips: " + chipsQty +
                            ", Drink: " + drinkQty +
                            ", Pop Corn: " + cornQty
            ).show();
            return;
        }

        chips.setQuantity(String.valueOf(chipsQty - packageQty));
        drink.setQuantity(String.valueOf(drinkQty - packageQty));
        corn.setQuantity(String.valueOf(cornQty - packageQty));

        cartList.removeIf(tm -> tm.getName().equalsIgnoreCase("Package"));

        double chipsPrice = Double.parseDouble(chips.getUnitPrice());
        double drinkPrice = Math.max(Double.parseDouble(drink.getUnitPrice()) - 50, 0);
        double cornPrice = Double.parseDouble(corn.getUnitPrice());

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

        btnRemove.setOnAction(ev -> {
            cartList.remove(packageItem);
            chips.setQuantity(String.valueOf(Integer.parseInt(chips.getQuantity()) + packageQty));
            drink.setQuantity(String.valueOf(Integer.parseInt(drink.getQuantity()) + packageQty));
            corn.setQuantity(String.valueOf(Integer.parseInt(corn.getQuantity()) + packageQty));
            tblCart.refresh();
            calculateTotal();
        });

        cartList.add(packageItem);
        tblCart.refresh();
        calculateTotal();
    }

    @FXML
    void btnCheckBalanceOnAction(ActionEvent e) {
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
    void btnPlaceOrderOnAction(ActionEvent e) {
        if (cartList.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Cart is empty!").show();
            return;
        }

        try {
            ObservableList<CartTm> items = FXCollections.observableArrayList(cartList);

            OrderDto orderDto = new OrderDto(
                    txtDate.getText(),
                    lblTotalAmount.getText(),
                    items
            );

            boolean success = orderBo.placeOrder(orderDto);
            if (success) {
                new Alert(Alert.AlertType.INFORMATION, "🎉 Order placed successfully!").show();
                clearAll();
                lblOrderId.setText("(Auto-generated)");
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to place order!").show();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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
        clearSelection();
    }

    @FXML
    void btnBackOnAction(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/lk/ijse/grim_fieldfood_stall/assests/DashBoard.fxml"));
        Stage stage = (Stage) rootVBox.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.setTitle("Dashboard");
        stage.show();
    }
}