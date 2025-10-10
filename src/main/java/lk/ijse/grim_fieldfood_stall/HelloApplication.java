package lk.ijse.grim_fieldfood_stall;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/lk/ijse/grim_fieldfood_stall/assests/DashBoard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 920, 740);
        stage.setTitle("Grim Theater");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}