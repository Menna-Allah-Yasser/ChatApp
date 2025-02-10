package com.chat.network;


import com.chat.entity.*;
import com.chat.utils.Cordinator;

import javafx.collections.ObservableList;

import javafx.application.Platform;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientImpl extends UnicastRemoteObject implements ClientRepository {


    public ClientImpl() throws RemoteException {
    }

    @Override

    public void getNotification(Notification notification) {
        Cordinator.getNotificationList().add(0, notification);

    public void getNotification(Notification notification)  throws  RemoteException{
       Platform.runLater(()->{Cordinator.getNotificationList().add(0,notification);});


    }

    @Override
    public void sendMessage(Message message) throws RemoteException {

    }

    @Override
    public void addedToGroup(int groupID) throws RemoteException {

    }

    @Override
    public void friendAcceptedRequest(int friendID) throws RemoteException {

    }

    @Override
    public void receivedFriendRequest(int friendID) throws RemoteException {

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
}
