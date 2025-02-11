package com.chat.network;


import com.chat.entity.*;
import com.chat.utils.Cordinator;

import javafx.beans.Observable;

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
        Platform.runLater(()->{Cordinator.getList().add(message);});
    }
    @Override
    public void receivedFriendRequest(Invitation invitation) throws RemoteException {
        if(invitation.getStatus()==InvStatus.WAIT)
        {
            Platform.runLater(()->Cordinator.getFriendRequestlist().add(0,invitation));
        }



    }

    @Override
    public void addedToGroup(int groupID) throws RemoteException {

    }

    @Override
    public void friendAcceptedRequest(int friendID) throws RemoteException {

    /*    ObservableList<CardItem> list = Cordinator.getContactList();
        for(CardItem friend :list)
        {
            if(friend.getId()==friendID)
            {
                friend.setisuser_isOnline(true);
            }
        }*/
    }

    @Override
    public void receivedFriendRequest(int friendID) throws RemoteException {

       /* ObservableList<CardItem> list = Cordinator.getContactList();
        for(CardItem friend :list)
        {
            if(friend.getId()==friendID)
            {
                friend.setisuser_isOnline(false);
            }
        }*/

    }

    @Override
    public void receiveAnnouncement(Announcement announcement) throws RemoteException {

    }


    @Override
    public void disconnect() throws RemoteException {



    @Override
    public void receiveAnnouncement(Announcement announcement) throws RemoteException {
        Platform.runLater(()->{Cordinator.getAlist().add(0,announcement);});}
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
