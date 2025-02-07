package com.chat.client.network;

import com.chat.entity.Announcement;
import com.chat.entity.Message;
import com.chat.entity.Notification;

import java.rmi.RemoteException;

public class ClientImpl implements ClientRepository {


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
