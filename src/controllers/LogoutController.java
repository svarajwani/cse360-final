package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;

public class LogoutController {

    @FXML
    private Label booksSoldLabel;

    @FXML
    private Label totalRevenueLabel;

    @FXML
    public void initialize() {
        int booksSold = 120; // Placeholder for actual data
        double totalRevenue = 1799.99;

        booksSoldLabel.setText("Books Sold: " + booksSold);
        totalRevenueLabel.setText("Total Revenue: $" + String.format("%.2f", totalRevenue));
    }

    public void handleLogout(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            double width = stage.getWidth();
            double height = stage.getHeight();

            Scene scene = new Scene(root, width, height);
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.setTitle("SunDevil Book Login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
