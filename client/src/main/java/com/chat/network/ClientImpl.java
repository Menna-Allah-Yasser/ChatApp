package com.chat.network;


import com.chat.entity.*;
import com.chat.utils.Cordinator;
import javafx.application.Platform;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientImpl extends UnicastRemoteObject implements ClientRepository {


    public ClientImpl() throws RemoteException {
    }

    @Override
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
    public void friendLoggedIn(int friendID) throws RemoteException {

    }

    @Override
    public void friendLoggedOut(int friendID) throws RemoteException {

    }

    @Override
    public void friendAcceptedRequest(int friendID) throws RemoteException {

    }



    @Override
    public void receiveAnnouncement(Announcement announcement) throws RemoteException {
        Platform.runLater(()->{Cordinator.getAlist().add(0,announcement);});

    }

    @Override
    public void disconnect() throws RemoteException {

    }
}
