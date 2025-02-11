package com.chat.controller;

import com.chat.entity.Announcement;
import com.chat.entity.Notification;
import com.chat.entity.User;
import com.chat.network.ServerConnection;
import com.chat.network.ServerRepository;
import com.chat.utils.Cordinator;
import com.chat.utils.CurrentChat;
import com.chat.utils.Director;
import com.chat.utils.SessionManager;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class AnnouncementController implements Initializable {
    @FXML
    private ListView<Object> list;
    ObservableList<Object> alist= Cordinator.getAlist();
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ServerRepository server= ServerConnection.getServer();

        Runnable r=()->{
            try {
                alist.setAll(server.getAllAnnouncememts());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            Platform.runLater(()->{

                list.setItems(alist);
            });
            //

        };
        Cordinator.getScheduledExecutorService().execute(r);



//        list.setCellFactory(param -> new ListCell<Object>() {
//            private final HBox content;
//            //private final ImageView avatar;
//
//            private final Label messageLabel;
//            private final Label timeLabel;
//
//
//            {
//                messageLabel = new Label();
//                messageLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
//                timeLabel=new Label();
//                timeLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
//                Region spacer = new Region();
//                HBox.setHgrow(spacer, Priority.ALWAYS);
//                content = new HBox(messageLabel,spacer,timeLabel);  //avatar,
//                content.setStyle("-fx-padding: 10px;");
//
//            }
//
//            @Override
//            protected void updateItem(Object item, boolean empty) {
//                super.updateItem(item, empty);
//                if (empty || item == null) {
//                    setText(null);
//                    setGraphic(null);
//                }
//                else if (item instanceof Notification) {
//                    // This is a notification
//                    Announcement announcement = (Announcement) item;
//
//
//                    timeLabel.setText(notification.getTime().toString());
//
//                    messageLabel.setText(notification.getDesc());
//
//
//                    if (notification.getStat()==Notification.status.UNREAD) {
//                        content.setStyle("-fx-background-color: #777777; -fx-padding: 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
//                    } else {
//                        content.setStyle("-fx-background-color: #FFFFFF; -fx-padding: 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
//                    }
//                    setOnMouseClicked(e->{
//                        if(notification.getStat()== Notification.status.UNREAD)
//                        {
//                            Runnable r=()->{ try {
//                                server.updateNotification(notification.getId(),SessionManager.getLoggedInUser());
//                            } catch (RemoteException ex) {
//                                throw new RuntimeException(ex);
//                            }};
//                            Cordinator.getScheduledExecutorService().execute(r);
//
//                            notification.setStat(Notification.status.READ);
//                            System.out.println(notification);
//                            content.setStyle("-fx-background-color: #FFFFFF; -fx-padding: 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
//                        }
//                        if(notification.getIsMessage())
//                        {
//                            CurrentChat.chatId=notification.getChat_id();
//                            Node node=  Director.loadView("home");
//                            Cordinator.barController.getCenterPane().setCenter(node);
//                        }
//
//                    });
//
//                    setText(null);
//                    setGraphic(content);
//                }
//            }
//        });


    }
}
