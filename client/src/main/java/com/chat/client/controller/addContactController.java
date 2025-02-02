package com.chat.client.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ListCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

public class addContactController implements Initializable {

    @FXML private Button btnAdd;
    @FXML private Button btnRequest;
    @FXML private ListView<String> contactList;
    @FXML private TextField phoneTextField;

    private ObservableList<String> contacts;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize the contact list with an empty observable list
        contacts = FXCollections.observableArrayList();
        contactList.setItems(contacts);

        // Set custom cell factory with background color for each ListCell
        contactList.setCellFactory(param -> new ListCell<String>() {
            private final AnchorPane anchorPane = new AnchorPane();
            private final TextField phoneText = new TextField();
            private final Button btnCancel = new Button("Cancel");

            {
                // UI Improvements
                phoneText.setFont(Font.font(14));
                btnCancel.setStyle("-fx-background-color: #F44336; -fx-text-fill: white; -fx-background-radius: 5;");

                // Handle the Cancel button action
                btnCancel.setOnAction(event -> contacts.remove(getItem()));

                // Position the phone number and Cancel button within the AnchorPane
                // Fixed positioning for phone number from the top
                //AnchorPane.setTopAnchor(phoneText, 3.0); // 5px from the top
                AnchorPane.setLeftAnchor(phoneText, 10.0); // 10px from the left
                //AnchorPane.setBottomAnchor(phoneText, 3.0);
                // Fixed positioning for Cancel button from the bottom
                //AnchorPane.setTopAnchor(btnCancel, 2.0); // 5px from the bottom
                AnchorPane.setRightAnchor(btnCancel, 10.0); // 10px from the right
                //AnchorPane.setBottomAnchor(phoneText, 2.0);
                // Set background color for the AnchorPane (light purple)
                //anchorPane.setStyle("-fx-background-color: #E1BBF3;");  // Light Purple Color

                // Add both components to the AnchorPane
                anchorPane.getChildren().addAll(phoneText, btnCancel);
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    phoneText.setText(item);
                    setGraphic(anchorPane);

                }
            }
        });

        // Handle Add button click event
        btnAdd.setOnAction(event -> {
            String phoneNumber = phoneTextField.getText().trim();
            if (!phoneNumber.isEmpty()) {
                contacts.add(phoneNumber);
                phoneTextField.clear();
            }
        });
    }
}
