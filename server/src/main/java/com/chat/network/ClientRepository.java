package com.chat.network;

import com.chat.entity.Announcement;
import com.chat.entity.Message;
import com.chat.entity.Notification;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientRepository extends Remote {

    public  void  getNotification(Notification notification) throws RemoteException;  //khaled

    public void sendMessage(Message message) throws RemoteException;

    void addedToGroup(int groupID) throws RemoteException;

    public void friendLoggedIn(int friendID) throws RemoteException;
    public void friendLoggedOut(int friendID) throws RemoteException;
    public void friendAcceptedRequest(int friendID) throws RemoteException;
    public void receivedFriendRequest(int friendID) throws RemoteException;
    public void receiveAnnouncement(Announcement announcement) throws RemoteException;

    public void disconnect() throws RemoteException;

}
