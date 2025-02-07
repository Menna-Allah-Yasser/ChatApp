package com.chat.controller;

import com.chat.entity.Notification;
import com.chat.network.ServerConnection;
import com.chat.network.ServerRepository;
import com.chat.utils.SessionManager;
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
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class NotificationController implements Initializable {

    @FXML
    private ListView<Notification> list;
    ObservableList<Notification>nlist= FXCollections.observableArrayList();

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



    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ServerRepository server= ServerConnection.getServer();
        try {
            nlist.setAll(server.getAllNotifications(SessionManager.getLoggedInUser()));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        list.setItems(nlist);
        list.setCellFactory(param -> new ListCell<Notification>() {
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
            protected void updateItem(Notification item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                }
                 else if (item instanceof Notification) {
                    // This is a notification
                    Notification notification = (Notification) item;
                    try {
                        senderLabel.setText(server.getUser(notification.getSenderId()).getName());
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                    messageLabel.setText(notification.getDesc());
                    avatar.setImage(new Image(getClass().getResourceAsStream(notification.getAvatarPath())));

                    if (notification.getStat()==Notification.status.UNREAD) {
                        content.setStyle("-fx-background-color: #E8E8E8; -fx-padding: 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
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
