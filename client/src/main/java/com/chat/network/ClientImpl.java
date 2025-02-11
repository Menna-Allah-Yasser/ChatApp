package com.chat.network;


import com.chat.entity.*;
import com.chat.utils.Cordinator;

import javafx.beans.Observable;

import javafx.collections.ObservableList;


import javafx.application.Platform;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;

public class ClientImpl extends UnicastRemoteObject implements ClientRepository {


    public ClientImpl() throws RemoteException {
    }

    @Override


    public void getNotification(Notification notification)  throws  RemoteException{
       Platform.runLater(()->{Cordinator.getNotificationList().add(0,notification);});


    }

    @Override
    public void sendMessage(Message message) throws RemoteException {



    }


    @Override
    public void moveaNewCardtoTop(ChatCard chatCard) throws RemoteException {

        Platform.runLater(()->{Cordinator.getContactList().add(0,new ChatCardClient(
                chatCard.getChat_id(),
                chatCard.getChat_name(),
                chatCard.getMessage_id(),
                chatCard.getMessage_desc(),
                chatCard.getMessage_time(),
                chatCard.getUser_Id(),
                chatCard.getUser_name(),
                chatCard.isUser_isOnline(),
                chatCard.getUser_pictrue()

        ));});

    }





    @Override
    public void receiveAnnouncement(Announcement announcement) throws RemoteException {

    }

    @Override
    public void disconnect() throws RemoteException {

    }

    @Override
    public void friendLoggedIn(int friendID) throws RemoteException {

        ObservableList<ChatCardClient> list = Cordinator.getContactList();

        for (ChatCardClient friend : list) {

            if (friend.getUserId() == friendID) {
                friend.setUserIsOnline(true);

            }
        }
    }

    @Override
    public void friendLoggedOut(int friendID) throws RemoteException {

        ObservableList<ChatCardClient> list = Cordinator.getContactList();
        for (ChatCardClient friend : list) {
            if (friend.getUserId()== friendID) {
                friend.setUserIsOnline(false);
            }

        }
    }
    @Override
    public void receivedMessage(Message message) throws RemoteException {

        ObservableList<ChatCardClient> list = Cordinator.getContactList();

        int senderId = message.getUser_id();
        for (ChatCardClient friend : list) {
            if (friend.getUserId()== senderId) {
             ChatCardClient client =  friend;
             list.removeIf(Friend->Friend.getUserId()==senderId);

             client.setMessageDesc(message.getDescription());
             client.setMessageId(message.getId());

             client.setMessageTime(message.getTime());
             list.add(0,client);
             break ;

            }

        }


    }

}
