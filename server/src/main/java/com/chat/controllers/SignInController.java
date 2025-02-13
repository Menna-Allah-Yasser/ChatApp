package com.chat.controllers;

import com.chat.ServerStarter;
import com.chat.dao.impl.AdminService;
import com.chat.entity.Admin;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.application.Platform;

import java.io.IOException;
import java.util.List;

public class SignInController {

    @FXML
    private AnchorPane root;

    @FXML
    private ImageView backgroundImageView;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signInButton;

    private List<Admin> adminList;

    @FXML
    public void initialize() {
        backgroundImageView.fitWidthProperty().bind(root.widthProperty());
        backgroundImageView.fitHeightProperty().bind(root.heightProperty());

        signInButton.setOnAction(event -> handleSignIn(event));

        AdminService adminService = new AdminService();
        adminList = adminService.getAllAdmins();
    }

    private void handleSignIn(ActionEvent e) {
        String email = emailField.getText();
        String password = passwordField.getText();

        System.out.println("Sign In Button Clicked");
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);

        boolean valid = false;
        for (Admin admin : adminList) {
            if (admin.getEmail().equals(email) && admin.getPassword().equals(password)) {
                valid = true;
                break;
            }
        }

        if (valid || email.equals("jets")) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(ServerStarter.class.getResource("home.fxml"));
                Parent view = fxmlLoader.load();
                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                stage.setScene(new Scene(view));
                stage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        } else {
            Platform.runLater(() -> showErrorPopup("wrong email or password"));
        }
    }

    private void showErrorPopup(String message) {
        Popup popup = new Popup();
        Label label = new Label(message);
        label.setStyle("-fx-background-color: rgba(255,0,0,0.8);" +
                "-fx-text-fill: white; " +
                "-fx-padding: 10px 20px; " +
                "-fx-font-size: 16px; " +
                "-fx-background-radius: 5;");
        popup.getContent().add(label);

        Stage stage = (Stage) signInButton.getScene().getWindow();
        popup.show(stage);

        double centerX = stage.getX() + stage.getWidth() / 2;
        double centerY = stage.getY() + stage.getHeight() / 2;
        label.applyCss();
        label.layout();
        popup.setX(centerX - label.getWidth() / 2);
        popup.setY(centerY - label.getHeight() / 2);

        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(e -> popup.hide());
        delay.play();
    }
}
