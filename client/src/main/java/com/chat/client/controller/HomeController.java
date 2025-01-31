package com.chat.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

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
    public void initialize() {

    }

}