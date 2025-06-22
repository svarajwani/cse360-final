package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import models.User;
import models.UserDatabase;

import java.util.HashMap;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label statusLabel;

    private final HashMap<String, User> users = UserDatabase.loadUsers();

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        User user = users.get(username);

        if (user != null && user.getPassword().equals(password)) {
            String role = user.getRole();
            try {
                Stage stage = (Stage) usernameField.getScene().getWindow();
                Parent root;

                if (role.equals("admin")) {
                    root = FXMLLoader.load(getClass().getResource("/views/admin.fxml"));
                } else {
                    root = FXMLLoader.load(getClass().getResource("/views/home.fxml"));
                }

                double width = stage.getWidth();
                double height = stage.getHeight();

                Scene scene = new Scene(root, width, height);
                stage.setScene(scene);
                stage.setMaximized(true);
                stage.setTitle(role.substring(0, 1).toUpperCase() + role.substring(1) + " Page");
            } catch (Exception e) {
                e.printStackTrace();
                statusLabel.setText("Error loading page.");
            }
        } else {
            statusLabel.setText("Invalid username or password.");
        }
    }
}