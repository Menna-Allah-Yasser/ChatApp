package com.chat.network;


import com.chat.entity.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientImpl extends UnicastRemoteObject implements ClientRepository {


    public ClientImpl() throws RemoteException {
    }

    @Override
    public void getNotification(Notification notification) {

    }

    @Override
    public void sendMessage(Message message) throws RemoteException {

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
    public void receivedFriendRequest(int friendID) throws RemoteException {

    }

    @Override
    public void receiveAnnouncement(Announcement announcement) throws RemoteException {

    }

    @Override
    public void disconnect() throws RemoteException {

    }
}
