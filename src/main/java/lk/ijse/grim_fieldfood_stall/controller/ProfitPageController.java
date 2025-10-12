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
import javafx.stage.Stage;
import lk.ijse.grim_fieldfood_stall.bo.BOFactory;
import lk.ijse.grim_fieldfood_stall.bo.custom.FoodBO;
import lk.ijse.grim_fieldfood_stall.bo.custom.OrderBo;
import lk.ijse.grim_fieldfood_stall.dto.FoodDto;
import lk.ijse.grim_fieldfood_stall.entity.OrderFood;
import lk.ijse.grim_fieldfood_stall.model.ProfitTm;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfitPageController {

    @FXML
    public TextField txtDate;
    @FXML
    private Button btnBack;

    @FXML
    private TableColumn<ProfitTm, String> colFoodId;

    @FXML
    private TableColumn<ProfitTm, String> colName;

    @FXML
    private TableColumn<ProfitTm, Integer> colQty;

    @FXML
    private TableColumn<ProfitTm, Double> colUnitPrice;

    @FXML
    private TableColumn<ProfitTm, Double> colTotalPrice;

    @FXML
    private TableColumn<ProfitTm, Double> colProfit;

    @FXML
    private TableView<ProfitTm> tblFood;

    @FXML
    private TextField txtTotalProfit;

  

    private final FoodBO foodBO = (FoodBO) BOFactory.getInstance().getBO(BOFactory.BOtypes.FOOD);
    private final OrderBo orderBO = (OrderBo) BOFactory.getInstance().getBO(BOFactory.BOtypes.ORDER);

    public void initialize() {
        setCellValueFactories();
        loadProfitTable();
        setCurrentDate();
    }

    private void setCellValueFactories() {
        colFoodId.setCellValueFactory(new PropertyValueFactory<>("foodId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        colProfit.setCellValueFactory(new PropertyValueFactory<>("profit"));
    }
    private void loadProfitTable() {
        ObservableList<ProfitTm> obList = FXCollections.observableArrayList();
        double totalProfit = 0;

        try {
            List<FoodDto> allFoods = foodBO.getAllFoods();
            List<OrderFood> orderFoodList = orderBO.getAllOrderFoods();

            Map<Integer, Integer> totalOrderQtyMap = new HashMap<>();

            for (OrderFood of : orderFoodList) {
                int foodId = Math.toIntExact(of.getFood().getFoodId());

                int orderQty = Integer.parseInt(of.getOrderQuantity());

                int currentQty = totalOrderQtyMap.getOrDefault(foodId, 0);
                totalOrderQtyMap.put(foodId, currentQty + orderQty);
            }

            for (FoodDto food : allFoods) {
                int id = Math.toIntExact(food.getFoodId());
                String name = food.getName();
                double unitSellingPrice = Double.parseDouble(food.getUnitPrice());
                double totalBuyingPrice = Double.parseDouble(food.getTotalPrice());
                int qty = Integer.parseInt(food.getQuantity());

                int totalOrderedQty = totalOrderQtyMap.getOrDefault(id, 0);

                double buyingPerUnit = totalBuyingPrice / qty;
                double profit = (unitSellingPrice - buyingPerUnit) * totalOrderedQty;

                obList.add(new ProfitTm(
                        String.valueOf(id),
                        name,
                        totalOrderedQty,
                        unitSellingPrice,
                        totalBuyingPrice,
                        profit
                ));
                totalProfit += profit;
            }

            tblFood.setItems(obList);
            txtTotalProfit.setText(String.format("%.2f", totalProfit));

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load profit data!").show();
        }
    }


    private void setCurrentDate() {
        txtDate.setText(LocalDate.now().toString());
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/lk/ijse/grim_fieldfood_stall/assests/DashBoard.fxml"));
        Parent root = loader.load();
        Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.setTitle("Dashboard");
        stage.show();
    }


}
