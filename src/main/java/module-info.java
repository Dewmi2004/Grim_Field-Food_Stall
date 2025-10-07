module lk.ijse.grim_fieldfood_stall {
    requires javafx.controls;
    requires javafx.fxml;


    opens lk.ijse.grim_fieldfood_stall to javafx.fxml;
    exports lk.ijse.grim_fieldfood_stall;
    exports lk.ijse.grim_fieldfood_stall.controller;
    opens lk.ijse.grim_fieldfood_stall.controller to javafx.fxml;
}