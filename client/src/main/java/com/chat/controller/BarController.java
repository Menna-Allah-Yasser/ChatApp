package com.chat.controller;

import com.chat.ClientStarter;
import com.chat.entity.User;
import com.chat.network.ServerConnection;
import com.chat.network.ServerRepository;
import com.chat.utils.Director;
import com.chat.utils.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.rmi.RemoteException;

public class BarController {

    @FXML
    private ImageView chat;

    @FXML
    private ImageView exit;

    @FXML
    private ImageView profileImg;

    @FXML
    private  BorderPane centerPane;

    private ServerRepository server;

    @FXML
    public void initialize() {

        server = ServerConnection.getServer();

       Node node =  Director.loadView("home");
       centerPane.setCenter(node);


       loadUserPic(8);


    }

    public void loadUserPic(int userId){
        User user = null;
        try {
            user = server.getUser(userId);
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
        if (user != null && user.getPicture() != null) {
            byte[] imageData = user.getPicture();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(imageData);
            Image image = new Image(inputStream);
            profileImg.setImage(image);
        }
    }

    public BorderPane getCenterPane() {
        return centerPane;
    }

    @FXML
    public void notificationClicked()
    {
        Node node=  Director.loadView("notification");
        centerPane.setCenter(node);
    }
    @FXML
    protected void onFriendRequestClick()
    {
        Node node=  Director.loadView("friendreq");
        centerPane.setCenter(node);

    }

    @FXML
    void onaddContactClick() {
        Node node=  Director.loadView("addContact");
        centerPane.setCenter(node);
    }

    @FXML
    void onChatClick(MouseEvent event) {
        Node node=  Director.loadView("home");
        centerPane.setCenter(node);
    }

    @FXML
    void onnewGroupClicked() {

        Node node =  Director.loadView("group");
        centerPane.setCenter(node);
    }
    @FXML
    void onAnnouncement() {

        Node node=  Director.loadView("announcement");
        centerPane.setCenter(node);
    }

    @FXML
    void onProfileClicked() {
        Node node=  Director.loadView("profile");
        centerPane.setCenter(node);
    }

    @FXML
    void onLogoClicked() {
        Node node=  Director.loadView("home");
        centerPane.setCenter(node);
    }

    @FXML
    void exitHandler(MouseEvent event) {

        ServerRepository server = ServerConnection.getServer();

        int id = SessionManager.getLoggedInUser();



        try {
            server.logout(id);

            SessionManager.clearSession();

            try {
                ClientStarter.setRoot("login");
            } catch (IOException e) {

            }

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }




    }

   public void setCenterPane(Pane pane){
        centerPane.setCenter(pane);
    }





}
