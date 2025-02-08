package com.chat.controller;

import com.chat.ClientStarter;
import com.chat.entity.User;
import com.chat.network.ClientImpl;
import com.chat.network.ServerConnection;
import com.chat.network.ServerRepository;
import com.chat.utils.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javafx.event.*;

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

    ServerRepository server;
    @FXML
    public void initialize()
    {
        server = ServerConnection.getServer();

        String PROPERTIES_FILE = "user_session.properties";

        File file = new File(PROPERTIES_FILE);

        if(file.exists())
        {
            Properties properties = new Properties();
            try (FileInputStream inputStream = new FileInputStream(file)){

                properties.load(inputStream);

                String phoneNumber = properties.getProperty("phoneNumber");

                userPhoneNumberSignIn.setText(phoneNumber);



            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void gotoHomePage(ActionEvent event) throws IOException {

        String phoneNumber= userPhoneNumberSignIn.getText();

        String  password = userPasswordSignIn.getText();

        if(server.authenticateUser(phoneNumber,password))
        {
           User user = server.getUser(phoneNumber);

           ClientImpl client = new ClientImpl();

           server.login(user.getUserId(),client);

            SessionManager.setLoggedInUser(user.getUserId(),user.getPhoneNumber(), user.getPassword());



        }

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
