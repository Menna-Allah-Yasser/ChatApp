package com.chat.controller;

import com.chat.ClientStarter;
import com.chat.entity.Notification;
import com.chat.entity.User;
import com.chat.network.ServerConnection;
import com.chat.network.ServerRepository;
import com.chat.utils.Cordinator;
import com.chat.utils.CurrentChat;
import com.chat.utils.Director;
import com.chat.utils.SessionManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class NotificationController implements Initializable {
    int idx=1;

    @FXML
    private ListView<Object> list;
    ObservableList<Object>nlist= Cordinator.getNotificationList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idx=0;
        ServerRepository server= ServerConnection.getServer();

            Runnable r=()->{
                try {
                    nlist.setAll(server.getAllNotifications(SessionManager.getLoggedInUser()));
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                    Platform.runLater(()->{

                        list.setItems(nlist);
                    });
                    //

            };
            Cordinator.getScheduledExecutorService().execute(r);



        list.setCellFactory(param -> new ListCell<Object>() {
            private final HBox content;
            //private final ImageView avatar;
            private final VBox textContainer;
            private final Label senderLabel;
            private final Label messageLabel;
            private final Label timeLabel;


            {
//                avatar = new ImageView();
//                avatar.setFitWidth(40);
//                avatar.setFitHeight(40);
//                avatar.setPreserveRatio(true);
                senderLabel = new Label();
                senderLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
                messageLabel = new Label();
                messageLabel.setStyle("-fx-font-size: 12px;");
                timeLabel=new Label();
                timeLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
                textContainer = new VBox(senderLabel, messageLabel);
                textContainer.setSpacing(5);
                Region spacer = new Region();
                HBox.setHgrow(spacer, Priority.ALWAYS);
                content = new HBox(textContainer ,spacer,timeLabel);  //avatar,
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
                    User user=null;
                    try {
                        user=server.getUser(notification.getSenderId());
                       senderLabel.setText(user.getName());

                    } catch (RemoteException e) {
                        System.out.println(e.getMessage());
                    }
                    timeLabel.setText(notification.getTime().toString());

                    messageLabel.setText(notification.getDesc());

//                    File file = new File("client/src/main/resources/server_images/" +"notification"+idx+".png" );
//                    idx++;
//                    file.getParentFile().mkdirs(); // Ensure directory exists
//                    try {
//                        Files.write(Paths.get(file.getAbsolutePath()), user.getPicture());
//                    } catch (IOException e) {
//                        System.out.println(e.getMessage());
//                    }
//                    avatar.setImage(new Image(getClass().getResourceAsStream(file.getAbsolutePath())));

                    if (notification.getStat()==Notification.status.UNREAD) {
                        content.setStyle("-fx-background-color: #777777; -fx-padding: 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
                    } else {
                        content.setStyle("-fx-background-color: #FFFFFF; -fx-padding: 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
                    }
                    setOnMouseClicked(e->{
                       if(notification.getStat()== Notification.status.UNREAD)
                       {
                           Runnable r=()->{ try {
                               server.updateNotification(notification.getId(),SessionManager.getLoggedInUser());
                           } catch (RemoteException ex) {
                               throw new RuntimeException(ex);
                           }};
                           Cordinator.getScheduledExecutorService().execute(r);

                           notification.setStat(Notification.status.READ);
                           System.out.println(notification);
                           content.setStyle("-fx-background-color: #FFFFFF; -fx-padding: 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
                       }
                       if(notification.getIsMessage())
                       {
                           CurrentChat.chatId=notification.getChat_id();
                           Node node=  Director.loadView("home");
                           Cordinator.barController.getCenterPane().setCenter(node);
                       }

                    });

                    setText(null);
                    setGraphic(content);
                }
            }
        });


    }
}
