package com.chat.controller;

import com.chat.entity.User;
import com.chat.network.ServerConnection;
import com.chat.network.ServerRepository;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
import org.controlsfx.control.textfield.CustomTextField;

import java.io.ByteArrayInputStream;
import java.rmi.RemoteException;
import java.util.List;
import java.util.stream.Collectors;

public class GroupController {

  /*  @FXML
    private Button cancelButton;

    @FXML
    private Button createButton;

    @FXML
    private TextField groupName;*/

    @FXML
    private ListView<User> userListView;

    private ServerRepository server ;

    /*@FXML
    public void initialize() {
        System.out.println("Group init");

        server = ServerConnection.getServer();

        try {
            List<User> friends = server.getAllFriends(1);
            ObservableList<User> friendsList = FXCollections.observableArrayList(friends);
            userListView.setItems(friendsList);

            userListView.setFixedCellSize(80);

            userListView.setCellFactory(param -> new ListCell<User>() {
                @Override
                protected void updateItem(User user, boolean empty) {
                    super.updateItem(user, empty);
                    if (empty || user == null) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        ImageView imageView;
                        if (user.getPicture() != null && user.getPicture().length > 0) {
                            Image image = new Image(new ByteArrayInputStream(user.getPicture()));
                            imageView = new ImageView(image);
                        } else {
                            Image defaultImage = new Image(getClass().getResourceAsStream("/images/default_user.png"));
                            imageView = new ImageView(defaultImage);
                        }
                        imageView.setFitHeight(40);
                        imageView.setFitWidth(40);

                        Label nameLabel = new Label(user.getName());
                        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

                        Label bioLabel = new Label(user.getBio() == null ? "No bio available" : user.getBio());
                        bioLabel.setStyle("-fx-text-fill: #666; -fx-font-size: 12px;");

                        HBox hBox = new HBox(10, imageView, new VBox(nameLabel, bioLabel));
                        hBox.setPadding(new Insets(5));
                        setGraphic(hBox);
                    }
                }
            });

        } catch (RemoteException e) {
            System.out.println("Error fetching friends: " + e.getMessage());
        }
    }
*/



   /* @FXML
    void onCancelAction(ActionEvent event) {

    }

    @FXML
    void onCreateAction(ActionEvent event) {

    }*/





}
