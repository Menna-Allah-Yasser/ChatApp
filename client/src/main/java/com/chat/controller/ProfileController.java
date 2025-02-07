package com.chat.controller;


import com.chat.network.ServerConnection;
import com.chat.network.ServerRepository;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import com.chat.entity.User;

import java.rmi.RemoteException;
import java.sql.Date;

public class ProfileController {
    @FXML
    private Label welcomeText;

    @FXML
    private TextField fNameTxt;

    @FXML
    private TextField email;


    @FXML
    private DatePicker DOB;

    @FXML
    private MenuButton gender;

    @FXML
    private MenuButton mode;

    @FXML
    void updateUserProfile() {

       ServerRepository serverRepository = ServerConnection.getServer();
       //serverRepository.updateUserImage(SessionManager.getLoggedInUser() , );
        User user = null;
        try {
            user = serverRepository.getUser(21);
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }

        user.setEmail(email.getText());
        try {
            serverRepository.updateUserInfo(user);
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
    }

}