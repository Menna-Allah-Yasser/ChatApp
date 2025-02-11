package com.chat.controller;

import com.chat.entity.Invitation;
import com.chat.network.ServerConnection;
import com.chat.network.ServerRepository;
import com.chat.utils.SessionManager;
import com.chat.utils.UserValidation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.Font;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class addContactController implements Initializable {

    @FXML private Button btnAdd;
    @FXML private Button btnRequest;
    @FXML private ListView<String> contactList;
    @FXML private TextField phoneTextField;

    private ObservableList<String> contacts;

    @FXML
    private Label warningLabel;

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

            ServerRepository server =ServerConnection.getServer();

            boolean isValid;


            try {
                 isValid = (server.getUser(phoneNumber)!=null)&& UserValidation.isValidPhoneNumber(phoneNumber)&&!phoneNumber.equals(server.getUser(SessionManager.getLoggedInUser()));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }

            if (isValid) {
                contacts.add(phoneNumber);
                phoneTextField.clear();
                warningLabel.setVisible(false);
            }
            else {
                warningLabel.setText("invalid number");
                applyWarningStyle(warningLabel);
                //  warningLabel.setStyle("-fx-text-fill: red;");
                warningLabel.setVisible(true);
            }
        });
    }
    private void applyWarningStyle(Label label) {
        label.setStyle(
                "-fx-text-fill: red;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14px;" +
                        "-fx-background-color: #ffe6e6;" +
                        "-fx-padding: 5px;" +
                        "-fx-border-color: red;" +
                        "-fx-border-radius: 5px;" +
                        "-fx-background-radius: 5px;"
        );
    }

    @FXML
    void sendRequsts(ActionEvent event) {

        int sender_id = SessionManager.getLoggedInUser();

        ServerRepository server = ServerConnection.getServer();

        List<Invitation> invitations = new ArrayList<>();

        Invitation invitation;

        for (String contact : contacts) {

            try {
             int receiver = server.getUser(contact).getUserId();

             invitation = new Invitation(sender_id,receiver);

             invitations.add(invitation);




            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }


        }

        try {
            server.sendFriendRequest(invitations);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }


    }
}
