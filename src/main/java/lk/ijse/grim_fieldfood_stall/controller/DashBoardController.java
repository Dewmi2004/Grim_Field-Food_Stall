package lk.ijse.grim_fieldfood_stall.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lk.ijse.grim_fieldfood_stall.bo.BOFactory;
import lk.ijse.grim_fieldfood_stall.bo.custom.FoodBO;
import lk.ijse.grim_fieldfood_stall.bo.custom.OrderBo;
import lk.ijse.grim_fieldfood_stall.dto.FoodDto;
import lk.ijse.grim_fieldfood_stall.dto.OrderDto;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DashBoardController implements Initializable {
private final FoodBO foodBO = (FoodBO) BOFactory.getInstance().getBO(BOFactory.BOtypes.FOOD);
private final OrderBo orderBO = (OrderBo) BOFactory.getInstance().getBO(BOFactory.BOtypes.ORDER);
    public AnchorPane Ank1;
    @FXML
    private Button btnManageFoods;

    @FXML
    private Button btnManageOrders;

    @FXML
    private Button btnManageProfit;

    @FXML
    private Label lblTotalFoods;

    @FXML
    private Label lblTotalOrders;

    @FXML
    private Label lblTotalProfit;

    @FXML
    void handleManageFoods(ActionEvent event) {
        nevigateTo("/lk/ijse/grim_fieldfood_stall/assests/food-Item.fxml");
    }

    @FXML
    void handleManageOrders(ActionEvent event) {
        nevigateTo("/lk/ijse/grim_fieldfood_stall/assests/place-Order.fxml");
    }

    @FXML
    void handleManageProfit(ActionEvent event) {
nevigateTo("/lk/ijse/grim_fieldfood_stall/assests/Profit.fxml");
    }
    private void nevigateTo(String s) {
        try {
            Ank1.getChildren().clear();
            AnchorPane pane = FXMLLoader.load(getClass().getResource(s));

            pane.prefWidthProperty().bind(Ank1.widthProperty());
            pane.prefHeightProperty().bind(Ank1.heightProperty());

            Ank1.getChildren().add(pane);
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,"Page Not Found!").show();
            e.printStackTrace();

        }
    }
    public void setFood () throws Exception {
        ArrayList<FoodDto> allFood = (ArrayList<FoodDto>) foodBO.getAllFoods();
        lblTotalFoods.setText(String.valueOf(allFood.size()));
    }
    public void setOrders () throws Exception {
        ArrayList<OrderDto> allOrders = (ArrayList<OrderDto>) orderBO.getAllOrders();
        lblTotalOrders.setText(String.valueOf(allOrders.size()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setFood();
            setOrders();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
