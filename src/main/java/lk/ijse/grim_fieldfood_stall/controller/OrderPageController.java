package lk.ijse.grim_fieldfood_stall.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class OrderPageController {

    @FXML
    private Button btnAddToCart;

    @FXML
    private Button btnCheckBalance;

    @FXML
    private Button btnPlaceOrder;

    @FXML

    private ComboBox<?> cmbItemId;

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
    private Label lblPaymentId;

    @FXML
    private Label lblTotalAmount;

    @FXML
    private TableView<?> tblCart;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtPaidAmount;

    @FXML
    private TextField txtQuantity;

    @FXML
    void btnAddtoCartOnAction(ActionEvent event) {

    }

    @FXML
    void btnCheckBalanceOnAction(ActionEvent event) {

    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {

    }

}
