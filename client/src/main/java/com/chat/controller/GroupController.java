package com.chat.controller;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
import org.controlsfx.control.textfield.CustomTextField;

public class GroupController {

    @FXML
    private TextField groupName;

    @FXML
    private Button cancelButton;

    @FXML
    private Button createButton;

    @FXML
    private ListView<String> userListView;

    @FXML
    private TextField searchTextField;

    @FXML
    public void initialize() {
        ObservableList<String> users = FXCollections.observableArrayList("sama", "roaa", "nada");
        userListView.setItems(users);
        userListView.setFixedCellSize(65);


        userListView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String user, boolean empty) {
                        super.updateItem(user, empty);
                        if (empty) {

                            HBox emptyBox = new HBox();
                            emptyBox.setVisible(false);
                            setGraphic(emptyBox);

                        } else {

                            CheckBox checkBox = new CheckBox();
                            checkBox.getStyleClass().add("check-box");

                            Image image = new Image(getClass().getResourceAsStream("/images/woman.png"));
                            ImageView imageView = new ImageView(image);
                            imageView.setFitHeight(30);
                            imageView.setFitWidth(30);

                            HBox hBox = new HBox(3);

                            hBox.getChildren().addAll(imageView, new javafx.scene.control.Label(user));

                            HBox.setMargin(checkBox, new Insets(0, 10, 10, 620));

                            hBox.getChildren().add(checkBox);

                            setGraphic(hBox);
                            hBox.getStyleClass().add("hbox-style");
                        }
                    }
                };
            }
        });
    }

    @FXML
    void cancelAction(ActionEvent event) {
        //

    }

    @FXML
    void onCtreateAction(ActionEvent event) {

        //

    }

    @FXML
    void filterListAction(ActionEvent event) {
        //
    }

}
