package com.chat.controller;

import com.chat.ClientStarter;
import com.chat.network.ServerConnection;
import com.chat.network.ServerRepository;
import com.chat.utils.Director;
import com.chat.utils.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

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

    @FXML
    public void initialize() {

       Node node =  Director.loadView("home");
        System.out.println("___________________________");
        System.out.println(node);
       centerPane.setCenter(node);


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

        Node node=  Director.loadView("group");
        centerPane.setCenter(node);
    }

    @FXML
    void onProfileClicked() {
        Node node=  Director.loadView("profile");
        centerPane.setCenter(node);
    }

    @FXML
    void onLogoClicked(MouseEvent event) {
        Node node=  Director.loadView("home");
        centerPane.setCenter(node);
    }

    @FXML
    void exitHandler(MouseEvent event) {

        ServerRepository  server = ServerConnection.getServer();

        int id = SessionManager.getLoggedInUser();

      //  ClientStarter.setRoot("signUp");

      /*  try {
            server.logout(id);

            SessionManager.clearSession();

            try {
                ClientStarter.setRoot("signUp");
            } catch (IOException e) {

            }

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

       */

    }





}
