package com.chat.client.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class FriendRequestController  {
   // Image accept=new Image(getClass().getResource("/images/accept.png").toExternalForm());
  //  Image decline=new Image(getClass().getResource("/images/decline.png").toExternalForm());
    @FXML
    private ListView<FriendRequest> list;
    ObservableList<FriendRequest> nlist= FXCollections.observableArrayList();


//    public void setStage(Stage stage) {
//        this.stage = stage;
//    }
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        for(int i=0;i<10;i++)nlist.add(new FriendRequest());
//
//    }
}
