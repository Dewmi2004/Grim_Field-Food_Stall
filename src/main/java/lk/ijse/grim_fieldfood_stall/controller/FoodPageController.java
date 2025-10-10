package lk.ijse.grim_fieldfood_stall.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class FoodPageController {

    @FXML
    private Button btnClearFood;

    @FXML
    private Button btnDeleteFood;

    @FXML
    private Button btnSaveFood;

    @FXML
    private Button btnUpdateFood;

    @FXML
    private TableColumn<?, ?> colFoodId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotalPrice;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableView<?> tblFood;

    @FXML
    private TextField txtFoodId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtTotalPrice;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    void btnClearFoodOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteFoodOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveFoodOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateFoodOnAction(ActionEvent event) {

    }

}
