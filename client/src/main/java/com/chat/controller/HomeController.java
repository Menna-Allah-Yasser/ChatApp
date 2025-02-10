package com.chat.controller;

import com.chat.entity.Message;
import com.chat.network.ServerConnection;
import com.chat.network.ServerRepository;
import com.chat.utils.Cordinator;
import com.chat.utils.CurrentChat;
import com.chat.utils.SessionManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import javax.swing.text.html.ImageView;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class HomeController {
    @FXML
    private Label all;

    @FXML
    private TextField search;

    @FXML
    private Label work;

    @FXML
    private Label currentChatName;

    @FXML
    private ListView<Object> chats;



    @FXML
    private Label fav;

    @FXML
    private BorderPane mainPane;

    @FXML
    private Label family;

    @FXML
    private ListView<Object> list;

    @FXML
    private TextField message;


    @FXML
    public void initialize() {
        if(CurrentChat.chatId>0)
        {
            renderMessages();
        }



    }
    public void renderMessages()
    {
        System.out.println("rendering message");
        ServerRepository server=ServerConnection.getServer();
      Runnable r=()->{

            try {
                List<Message> messages=server.getChatMessages(CurrentChat.chatId);
                Cordinator.getList().setAll(messages);
                System.out.println(messages);
            } catch (RemoteException e) {
                throw new RuntimeException("here");
            }
            Platform.runLater(()->{list.setItems(Cordinator.getList());});


       };
  Cordinator.getScheduledExecutorService().execute(r);

Runnable r2=()-> {
    try {

        CurrentChat.participants = server.getChatParticipants(CurrentChat.chatId);
        //System.out.println(CurrentChat.chat.getName());
    } catch (RemoteException e) {
        System.out.println(e.getMessage());

    }

    if (CurrentChat.participants.size() == 2) {
        System.out.println("hi");
        if (CurrentChat.participants.get(0).getParticpantId() != SessionManager.getLoggedInUser()) {
            try {
                CurrentChat.user = server.getUser(CurrentChat.participants.get(0).getParticpantId());

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            try {
                System.out.println("hi");
                CurrentChat.user = server.getUser(CurrentChat.participants.get(1).getParticpantId());
            } catch (RemoteException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    else
    {
        try {
            CurrentChat.chat=server.getChatById(CurrentChat.chatId);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
    System.out.println(CurrentChat.chat);
    System.out.println(CurrentChat.chat.getName());

    if(CurrentChat.user==null)currentChatName.setText(CurrentChat.chat.getName());
    else currentChatName.setText(CurrentChat.chat.getName());
};
Cordinator.getScheduledExecutorService().execute(r2);




 //System.out.println(Cordinator.getList());

   }




}