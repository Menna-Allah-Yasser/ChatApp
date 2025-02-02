package com.chat.client.controller;

import com.chat.client.ClientStarter;
import com.chat.client.utils.Director;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.IOException;

import javafx.event.*;
import javafx.scene.layout.Pane;

public class LoginController {

    @FXML
    private Button signInbutton;

    @FXML
    private PasswordField userPasswordSignIn;

    @FXML
    private Hyperlink SignUpNavigationButton;

    @FXML
    private TextField userPhoneNumberSignIn;

    @FXML
    private ImageView imageView;

    @FXML
    void gotoHomePage(ActionEvent event) throws IOException {

        //check authoration

        //send id to home
        ClientStarter.setRoot("Bar");

    }


    @FXML
    void goToSignUpPage(ActionEvent event)  {

        try {
            ClientStarter.setRoot("signUp");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
