package lk.ijse.grim_fieldfood_stall.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.grim_fieldfood_stall.bo.BOFactory;
import lk.ijse.grim_fieldfood_stall.bo.custom.FoodBO;
import lk.ijse.grim_fieldfood_stall.dto.FoodDTO;
import lk.ijse.grim_fieldfood_stall.model.FoodTM;

import java.util.List;
import java.util.Optional;

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
    private TableView<FoodTM> tblFood;

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

    private final FoodBO foodBO = (FoodBO) BOFactory.getInstance().getBO(BOFactory.BOtypes.FOOD);

    public void initialize() {
        setCellValueFactory();
        loadAllFoods();
    }

    private void setCellValueFactory() {
        colFoodId.setCellValueFactory(new PropertyValueFactory<>("foodId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
    }

    private void loadAllFoods() {
        ObservableList<FoodTM> obList = FXCollections.observableArrayList();
        List<FoodDTO> dtoList = foodBO.getAllFoods();

        for (FoodDTO dto : dtoList) {
            obList.add(new FoodTM(
                    dto.getFoodId(),
                    dto.getName(),
                    dto.getQuantity(),
                    dto.getUnitPrice(),
                    dto.getTotalPrice()
            ));
        }
        tblFood.setItems(obList);
    }

    @FXML
     void clickOnAction(MouseEvent event) {
        FoodTM selected = tblFood.getSelectionModel().getSelectedItem();
        if (selected != null) {
            txtFoodId.setText(String.valueOf(selected.getFoodId()));
            txtName.setText(selected.getName());
            txtQuantity.setText(selected.getQuantity());
            txtUnitPrice.setText(selected.getUnitPrice());
            txtTotalPrice.setText(selected.getTotalPrice());

        }
    }

    @FXML
    void btnSaveFoodOnAction(ActionEvent event) {
        if (isInputValid()) {
            FoodDTO dto = new FoodDTO(
                    txtName.getText(),
                    txtQuantity.getText(),
                    txtUnitPrice.getText(),
                    txtTotalPrice.getText()
            );

            boolean isSaved = foodBO.saveFood(dto);
            if (isSaved) {
                showAlert(Alert.AlertType.INFORMATION, "Food saved successfully!");
                clearFields();
                loadAllFoods();
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed to save food.");
            }
        }
    }

    @FXML
    void btnUpdateFoodOnAction(ActionEvent event) {
        if (isInputValid()) {
            long id = Long.parseLong(txtFoodId.getText());

            FoodDTO dto = new FoodDTO(
                        id,
                    txtName.getText(),
                    txtQuantity.getText(),
                    txtUnitPrice.getText(),
                    txtTotalPrice.getText()
            );

            boolean isUpdated = foodBO.updateFood(dto);
            if (isUpdated) {
                showAlert(Alert.AlertType.INFORMATION, "Food updated successfully!");
                clearFields();
                loadAllFoods();
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed to update food.");
            }
        }
    }

    @FXML
    void btnDeleteFoodOnAction(ActionEvent event) {
        String id = txtFoodId.getText();

        if (id.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Please enter a Food ID to delete.");
            return;
        }

        Optional<ButtonType> result = showConfirm("Are you sure you want to delete this food?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean isDeleted = foodBO.deleteFood(id);
            if (isDeleted) {
                showAlert(Alert.AlertType.INFORMATION, "Food deleted successfully!");
                clearFields();
                loadAllFoods();
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed to delete food.");
            }
        }
    }

    @FXML
    void btnClearFoodOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtFoodId.clear();
        txtName.clear();
        txtQuantity.clear();
        txtUnitPrice.clear();
        txtTotalPrice.clear();
        tblFood.getSelectionModel().clearSelection();
    }

    private boolean isInputValid() {
        if (txtName.getText().isEmpty() ||
                txtQuantity.getText().isEmpty() ||
                txtUnitPrice.getText().isEmpty() ||
                txtTotalPrice.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Please fill in all fields.");
            return false;
        }
        return true;
    }

    private void showAlert(Alert.AlertType type, String message) {
        new Alert(type, message).show();
    }

    private Optional<ButtonType> showConfirm(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.OK, ButtonType.CANCEL);
        return alert.showAndWait();
    }
}
