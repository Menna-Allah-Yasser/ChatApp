package com.chat.controller;

import com.chat.entity.InvStatus;
import com.chat.entity.Invitation;
import com.chat.entity.Notification;
import com.chat.entity.User;
import com.chat.network.ServerConnection;
import com.chat.network.ServerRepository;
import com.chat.utils.Cordinator;
import com.chat.utils.CurrentChat;
import com.chat.utils.Director;
import com.chat.utils.SessionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;


import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;

public class FriendRequestController implements Initializable {
   Image accept1=new Image(getClass().getResource("/images/accept.png").toExternalForm());
   Image decline1=new Image(getClass().getResource("/images/remove.png").toExternalForm());
   ServerRepository server= ServerConnection.getServer();
    @FXML
    private ListView<Object> list;
    ObservableList<Object> flist= Cordinator.getFriendRequestlist();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Runnable r=()->{
            List<Invitation> list1;
            try {

                    list1=server.getAllInvitation(SessionManager.getLoggedInUser());
                    flist.setAll(list1);

                list.setItems(flist);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        };
        Cordinator.getScheduledExecutorService().execute(r);


        list.setCellFactory(e->new ListCell<>(){
            private final HBox content;
            private final VBox textContainer;
            private final Label senderLabel;
            private final Label messageLabel;
            private final ImageView accept;
            private final ImageView reject;


            {
                accept=new ImageView();
                accept.setFitWidth(40);
                accept.setFitHeight(40);
                accept.setPreserveRatio(true);
                reject=new ImageView();
                reject.setFitWidth(40);
                reject.setFitHeight(40);
                reject.setPreserveRatio(true);

                senderLabel = new Label();
                senderLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
                messageLabel = new Label();
                messageLabel.setStyle("-fx-font-size: 12px;");

                textContainer = new VBox(senderLabel, messageLabel);
                textContainer.setSpacing(5);
                Region spacer = new Region();
                HBox.setHgrow(spacer, Priority.ALWAYS);

                content = new HBox(textContainer ,spacer,accept,reject);
                content.setStyle("-fx-padding: 10px;");


            }

            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                }
                else  {

                    Invitation invitation = (Invitation) item;
                    User user=null;
                    try {
                        user=server.getUser(invitation.getSenderId());
                        senderLabel.setText(user.getName());

                    } catch (RemoteException e) {
                        System.out.println(e.getMessage());
                    }
                    accept.setImage(accept1);
                    reject.setImage(decline1);

                    messageLabel.setText("sent you a friend request");
                    content.setStyle("-fx-background-color: #FFFFFF; -fx-padding: 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;");

                    accept.setOnMouseClicked(e->{
                        //content.setStyle("-fx-background-color: #FF0000; -fx-padding: 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
                        invitation.setStatus(InvStatus.ACCEPT);

                        Runnable r2=()->{
                            try {
                                server.updateFriendsRequestStatus(invitation);
                            } catch (RemoteException ex) {
                                throw new RuntimeException(ex);
                            }
                        };
                        Cordinator.getScheduledExecutorService().execute(r2);
                        flist.remove(invitation);

                    });
                    reject.setOnMouseClicked(e->{
                      //  content.setStyle("-fx-background-color: #00FF00; -fx-padding: 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
                        invitation.setStatus(InvStatus.REJECT);
                        flist.remove(invitation);
                        Runnable r2=()->{
                            try {
                                server.updateFriendsRequestStatus(invitation);
                            } catch (RemoteException ex) {
                                throw new RuntimeException(ex);
                            }
                        };
                        Cordinator.getScheduledExecutorService().execute(r2);

                        flist.remove(invitation);

                    });

                    setText(null);
                    setGraphic(content);
                }
            }
        });


    }
}

