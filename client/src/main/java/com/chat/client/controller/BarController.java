package com.chat.client.controller;

import com.chat.client.utils.Director;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class BarController {

    @FXML
    private ImageView chat;

    @FXML
    private ImageView exit;

    @FXML
    private ImageView profileImg;

    @FXML
    private BorderPane centerPane;

    @FXML
    public void initialize() {

       Node node =  Director.loadView("home");
        System.out.println("___________________________");
        System.out.println(node);
       centerPane.setCenter(node);


    }



}
