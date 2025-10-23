package lk.ijse.grim_fieldfood_stall.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lk.ijse.grim_fieldfood_stall.config.FactoryConfiguration;
import lk.ijse.grim_fieldfood_stall.entity.OrderEntity;
import lk.ijse.grim_fieldfood_stall.entity.OrderFood;
import lk.ijse.grim_fieldfood_stall.model.ProfitTm;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class ProfitPageController {

    @FXML
    private Button btnBack;

    @FXML
    private TableColumn<ProfitTm, Long> colOrderId;

    @FXML
    private TableColumn<ProfitTm, Integer> colQuantity;

    @FXML
    private TableColumn<ProfitTm, Double> colProfit;

    @FXML
    private TableView<ProfitTm> tblFood;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtTotalProfit;

    private final ObservableList<ProfitTm> profitList = FXCollections.observableArrayList();
    private static double totalProfitValue = 0.0;

    @FXML
    public void initialize() {
        txtDate.setText(LocalDate.now().toString());
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colProfit.setCellValueFactory(new PropertyValueFactory<>("profit"));

        loadProfitData();
    }

    private void loadProfitData() {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            Query<OrderEntity> query = session.createQuery("FROM OrderEntity", OrderEntity.class);
            List<OrderEntity> orderList = query.list();

            profitList.clear();
            totalProfitValue = 0.0;

            for (OrderEntity order : orderList) {
                int totalQuantity = 0;

                for (OrderFood of : order.getOrderFoods()) {
                    totalQuantity += Integer.parseInt(of.getOrderQuantity());
                }

                double profit = Double.parseDouble(order.getTotalAmount());
                totalProfitValue += profit;

                profitList.add(new ProfitTm(order.getOrderId(), totalQuantity, profit));
            }

            tblFood.setItems(profitList);
            txtTotalProfit.setText(String.format("%.2f", totalProfitValue));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getTotalProfitValue() {
        return String.format("%.2f", totalProfitValue);
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/lk/ijse/grim_fieldfood_stall/assests/DashBoard.fxml")));
        Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.setTitle("Dashboard");
        stage.show();
    }
}
