package com.chat.controllers;

import com.chat.dao.impl.UserService;
import com.chat.entity.User;
import com.chat.service.impl.UserServerImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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

        // TODO: Add further validation and registration logic.
        UserService uService = new UserService();
        uService.addNewUser(user);
    }


}
