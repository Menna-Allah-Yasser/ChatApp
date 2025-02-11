package com.chat.controllers;

import com.chat.ServerStarter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private Button btnOne;

    @FXML
    private Button btnTwo;

    @FXML
    private Button btnThree;

    @FXML
    private BorderPane mainPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void btnOneHandler(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ServerStarter.class.getResource("Announcments.fxml"));
        Node view = Director.loadView("Announcments.fxml");
        mainPane.setCenter(view);
    }

    public void btnTwoHandler(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ServerStarter.class.getResource("statsBar.fxml"));
        Node view =fxmlLoader.load();
        mainPane.setCenter(view);
    }

    public void btnThreeHandler(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ServerStarter.class.getResource("server.fxml"));
        Node view =fxmlLoader.load();
        mainPane.setCenter(view);
    }

    public void btnFourHandler(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ServerStarter.class.getResource("register.fxml"));
        Node view =fxmlLoader.load();
        mainPane.setCenter(view);
    }


}
