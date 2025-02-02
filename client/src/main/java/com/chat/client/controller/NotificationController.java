package com.chat.client.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NotificationController implements Initializable {

    @FXML
    private ListView<Object> list;
    ObservableList<Object>nlist= FXCollections.observableArrayList();

    private Stage stage;
    public void setStage(Stage stage)
    {
        this.stage=stage;
    }
    public void onProfileClick()
    {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("profile.fxml"));
        try {
            System.out.println(loader.getLocation());
            Parent p=loader.load();

            stage.setScene(new Scene(p));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    protected void onFriendRequestClick()
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("friendreq.fxml"));

        try {
            Parent p=loader.load();
            System.out.println(loader);
            FriendRequestController c1=loader.getController();
            System.out.println(c1);
            c1.setStage(stage);
            stage.setScene(new Scene(p));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        for(int i=0;i<10;i++)nlist.add(new Notification());

        for(int i=0;i<10;i++)nlist.add(new Notification(false));
        list.setItems(nlist);
        list.setCellFactory(param -> new ListCell<Object>() {
            private final HBox content;
            private final ImageView avatar;
            private final VBox textContainer;
            private final Label senderLabel;
            private final Label messageLabel;


            {
                avatar = new ImageView();
                avatar.setFitWidth(40);
                avatar.setFitHeight(40);
                avatar.setPreserveRatio(true);
                senderLabel = new Label();
                senderLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
                messageLabel = new Label();
                messageLabel.setStyle("-fx-font-size: 12px;");

                textContainer = new VBox(senderLabel, messageLabel);
                textContainer.setSpacing(5);

                content = new HBox(10, avatar, textContainer);
                content.setStyle("-fx-padding: 10px;");

            }

            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                }
                 else if (item instanceof Notification) {
                    // This is a notification
                    Notification notification = (Notification) item;
                    senderLabel.setText(notification.getSender());
                    messageLabel.setText(notification.getMessage());
                    avatar.setImage(new Image(getClass().getResourceAsStream(notification.getAvatarPath())));

                    if (notification.isUnread()) {
                        //content.setStyle("-fx-background-color: #E8E8E8; -fx-padding: 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
                    } else {
                        content.setStyle("-fx-background-color: #FFFFFF; -fx-padding: 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
                    }

                    setText(null);
                    setGraphic(content);
                }
            }
        });


    }
}
