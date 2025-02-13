package com.chat.network;

import com.chat.entity.Announcement;

import com.chat.entity.Invitation;

import com.chat.entity.ChatCard;

import com.chat.entity.Message;
import com.chat.entity.Notification;
import javafx.fxml.Initializable;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientRepository extends Remote {


    public  void  getNotification(Notification notification) throws RemoteException;

    public void receivedFriendRequest(Invitation invitation) throws RemoteException;

    public void sendMessage(Message message) throws RemoteException;



    public void friendAcceptedRequest(int friendID) throws RemoteException;

    public void receiveAnnouncement(Announcement announcement) throws RemoteException;




    public void moveaNewCardtoTop(ChatCard chatCard) throws RemoteException ;



    public void disconnect() throws RemoteException;
    public void friendLoggedIn(int friendID) throws RemoteException;


    public void friendLoggedOut(int friendID) throws RemoteException ;

    public void receivedMessage(Message message) throws RemoteException ;




}
