module lk.ijse.grim_fieldfood_stall {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;

    opens lk.ijse.grim_fieldfood_stall to javafx.fxml;
    exports lk.ijse.grim_fieldfood_stall;
    exports lk.ijse.grim_fieldfood_stall.controller;
    opens lk.ijse.grim_fieldfood_stall.controller to javafx.fxml;
    opens lk.ijse.grim_fieldfood_stall.entity to org.hibernate.orm.core;
    opens lk.ijse.grim_fieldfood_stall.model to javafx.base;
    opens lk.ijse.grim_fieldfood_stall.dto to javafx.base;
    opens lk.ijse.grim_fieldfood_stall.bo to javafx.base;
    opens lk.ijse.grim_fieldfood_stall.dao to javafx.base;
}