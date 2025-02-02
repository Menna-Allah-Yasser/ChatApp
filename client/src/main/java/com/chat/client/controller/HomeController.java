package com.chat.client.controller;

import com.chat.client.utils.Director;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class HomeController {

    @FXML
    private ListView<String> messageList; // Adjust the type as needed

    @FXML
    private TextField searchField; // For searching messages or contacts

    @FXML
    private TextField messageInput;

    @FXML
    private Label contactName;

    @FXML
    private HBox messageInputContainer; // Container for input elements

    @FXML
    private GridPane chatPane;

    @FXML
    private BorderPane mainPane;

    @FXML
    private ImageView profileImg;

    @FXML
    public void initialize() {

        profileImg.setOnMouseClicked((event)->{
           Node node=  Director.loadView("profile.fxml");
           mainPane.setCenter(node);
        });
    }
    @FXML
    public void notificationClicked()
    {
        Node node=  Director.loadView("notification");
        mainPane.setCenter(node);
    }
    @FXML
    protected void onFriendRequestClick()
    {
        Node node=  Director.loadView("friendreq");
        mainPane.setCenter(node);

    }



}