package com.chat.controllers;

import com.chat.dao.impl.UserService;
import com.chat.entity.User;
import com.chat.service.impl.UserServerImpl;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML private TextField userPhoneNumber;
    @FXML private TextField userName;
    @FXML private TextField userEmail;
    @FXML private TextField userBio;
    @FXML private PasswordField userPassword;
    @FXML private TextField userGender;
    @FXML private TextField userCountry;
    @FXML private DatePicker userBirthDate;
    @FXML private Button submitButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    private void handleSubmit(ActionEvent event) {
        User user = new User();
        user.setPhoneNumber(userPhoneNumber.getText());
        user.setName(userName.getText());
        user.setEmail(userEmail.getText());
        user.setBio(userBio.getText());
        user.setPassword(userPassword.getText());
        user.setGender(userGender.getText());
        user.setCountry(userCountry.getText());
        user.setDob((userBirthDate.getValue() != null) ? userBirthDate.getValue(): LocalDate.now());
        user.setCountOfLogin(0);
        user.setIsOnline(false);
        user.setFacebookUrl("");
        user.setLinkedinUrl("");
        user.setTwitterUrl("");
        user.setPicture(null);
        user.setIsChatbotEnabled(false);
        user.setMode("AVALIABLE");

        System.out.println("Sign Up Details:");
        System.out.println(user);

        UserService uService = new UserService();
        try {
            uService.addNewUser(user);
            showErrorPopup("user added successfully", 1);
        } catch (Exception e) {
            showErrorPopup("some fields are empty or invalid", 0);
            throw new RuntimeException(e);
        }


    }
    private void showErrorPopup(String message, int color) {
        Popup popup = new Popup();
        Label label = new Label(message);

        // Base style
        String baseStyle = "-fx-text-fill: white; " +
                "-fx-padding: 10px 20px; " +
                "-fx-font-size: 16px; " +
                "-fx-background-radius: 5;";

        // Set background color based on the color parameter and combine with base style.
        String bgColorStyle;
        if (color == 1) {
            bgColorStyle = "-fx-background-color: rgba(0,255,0,0.8);"; // Green background.
        } else {
            bgColorStyle = "-fx-background-color: rgba(255,0,0,0.8);"; // Red background.
        }

        label.setStyle(baseStyle + bgColorStyle);
        popup.getContent().add(label);

        // Ensure 'submitButton' is defined. Replace with another node if necessary.
        Stage stage = (Stage) submitButton.getScene().getWindow();
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
