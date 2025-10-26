package lk.ijse.grim_fieldfood_stall.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lk.ijse.grim_fieldfood_stall.bo.BOFactory;
import lk.ijse.grim_fieldfood_stall.bo.custom.FoodBO;
import lk.ijse.grim_fieldfood_stall.dto.FoodDto;
import lk.ijse.grim_fieldfood_stall.model.FoodTM;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class FoodPageController {

    @FXML
    private Button btnBack, btnClearFood, btnDeleteFood, btnSaveFood, btnUpdateFood;

    @FXML
    private TableColumn<FoodTM, Long> colFoodId;

    @FXML
    private TableColumn<FoodTM, String> colName;

    @FXML
    private TableColumn<FoodTM, String> colQty;

    @FXML
    private TableColumn<FoodTM, String> colUnitPrice;

    @FXML
    private TableView<FoodTM> tblFood;

    @FXML
    private TextField txtFoodId, txtName, txtQuantity, txtUnitPrice;

    private final FoodBO foodBO = (FoodBO) BOFactory.getInstance().getBO(BOFactory.BOtypes.FOOD);

    @FXML
    public void initialize() {
        setCellValueFactory();
        loadAllFoods();
        btnDeleteFood.setDisable(true);
        btnUpdateFood.setDisable(true);
    }

    private void setCellValueFactory() {
        colFoodId.setCellValueFactory(new PropertyValueFactory<>("foodId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
    }

    private void loadAllFoods() {
        ObservableList<FoodTM> obList = FXCollections.observableArrayList();
        List<FoodDto> dtoList = foodBO.getAllFoods();

        for (FoodDto dto : dtoList) {
            obList.add(new FoodTM(dto.getFoodId(), dto.getName(), dto.getQuantity(), dto.getUnitPrice()));
        }
        tblFood.setItems(obList);
    }

    private boolean isInputValid() {
        if (txtName.getText().isEmpty() || txtQuantity.getText().isEmpty() || txtUnitPrice.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Please fill in all fields.");
            return false;
        }

        if (!txtName.getText().matches("[A-Za-z ]+")) {
            showAlert(Alert.AlertType.WARNING, "Name must contain only letters and spaces!");
            return false;
        }
        if (!txtQuantity.getText().matches("\\d+(\\.\\d{1,2})?")) {
            showAlert(Alert.AlertType.WARNING, "Quantity must be a valid number (up to 2 decimals)!");
            return false;
        }
        if (!txtUnitPrice.getText().matches("\\d+(\\.\\d{1,2})?")) {
            showAlert(Alert.AlertType.WARNING, "Unit Price must be a valid number (up to 2 decimals)!");
            return false;
        }
        return true;
    }

    @FXML
    void clickOnAction(MouseEvent event) {
        FoodTM selected = tblFood.getSelectionModel().getSelectedItem();
        if (selected != null) {
            txtFoodId.setText(String.valueOf(selected.getFoodId()));
            txtName.setText(selected.getName());
            txtQuantity.setText(selected.getQuantity());
            txtUnitPrice.setText(selected.getUnitPrice());
            btnSaveFood.setDisable(true);
            btnDeleteFood.setDisable(false);
            btnUpdateFood.setDisable(false);

        }
    }

    @FXML
    void btnSaveFoodOnAction(ActionEvent event) {
        if (isInputValid()) {
            FoodDto dto = new FoodDto(txtName.getText(), txtQuantity.getText(), txtUnitPrice.getText());
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
            FoodDto dto = new FoodDto(id, txtName.getText(), txtQuantity.getText(), txtUnitPrice.getText());
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
        tblFood.getSelectionModel().clearSelection();
    }

    private void showAlert(Alert.AlertType type, String message) {
        new Alert(type, message).show();
    }

    private Optional<ButtonType> showConfirm(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.OK, ButtonType.CANCEL);
        return alert.showAndWait();
    }

    @FXML
    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/lk/ijse/grim_fieldfood_stall/assests/DashBoard.fxml"));
        Parent root = loader.load();
        Stage stage = ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow());
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.setTitle("Dashboard");
        stage.show();
    }
}
