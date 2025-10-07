package lk.ijse.grim_fieldfood_stall.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class OrderPageController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}