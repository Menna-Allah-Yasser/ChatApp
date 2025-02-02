package com.chat.client.controller;

import com.chat.client.utils.Director;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
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
    private BorderPane mainPane;

    @FXML
    private ImageView profileImg;

    @FXML
<<<<<<< HEAD
    private ImageView chat;

    @FXML
    private ImageView exit;
=======
    private ImageView addContactBtn;
>>>>>>> 2f1c9059748755473ee66f2c000c6650acaf64a9

    @FXML
    public void initialize() {

<<<<<<< HEAD
=======
        profileImg.setOnMouseClicked((event)->{
           Node node =  Director.loadView("profile");
           mainPane.setCenter(node);
        });

        addContactBtn.setOnMouseClicked((event)->{
            Node node =  Director.loadView("addContact");
            mainPane.setCenter(node);
        });
>>>>>>> 2f1c9059748755473ee66f2c000c6650acaf64a9
    }



}