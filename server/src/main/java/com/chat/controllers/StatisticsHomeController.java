package com.chat.controllers;

import com.chat.ServerStarter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StatisticsHomeController implements Initializable {

    @FXML
    private Button btnCountry;

    @FXML
    private Button btnGender;

    @FXML
    private Button btnOnline;

    @FXML
    private BorderPane mainStatPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void btnOnlineHandler(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ServerStarter.class.getResource("online.fxml"));
        Node view = fxmlLoader.load();
        mainStatPane.setCenter(view);
    }

    public void btnGenderHandler(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ServerStarter.class.getResource("gender.fxml"));
        Node view = fxmlLoader.load();
        mainStatPane.setCenter(view);
    }

    public void btnCountryHandler(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ServerStarter.class.getResource("country.fxml"));
        Node view = fxmlLoader.load();
        mainStatPane.setCenter(view);
    }

}
