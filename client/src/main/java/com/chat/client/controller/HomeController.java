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
    public void initialize() {


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