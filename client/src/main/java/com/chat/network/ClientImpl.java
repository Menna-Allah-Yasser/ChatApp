package com.chat.network;


import com.chat.entity.*;
import com.chat.utils.Cordinator;

import com.chat.utils.CurrentChat;
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


    public void getNotification(Notification notification)  throws  RemoteException {
        Platform.runLater(() -> {
            Cordinator.getNotificationList().add(0, notification);
        });
    }




    @Override
    public void sendMessage(Message message) throws RemoteException {
        Platform.runLater(()->{Cordinator.getList().add(message);});
    }

    @Override
    public void friendAcceptedRequest(int friendID) throws RemoteException {

    }

    @Override
    public void receivedFriendRequest(Invitation invitation) throws RemoteException {
        if(invitation.getStatus()==InvStatus.WAIT)
        {
            Platform.runLater(()->Cordinator.getFriendRequestlist().add(0,invitation));
        }



    }


    @Override
    public void moveaNewCardtoTop(ChatCard chatCard) throws RemoteException {

        Platform.runLater(()->{Cordinator.getContactList().add(0,new ChatCardClient(
                chatCard.getChat_id(),
                chatCard.getChat_name(),
                chatCard.getUser_Id(),
                chatCard.getUser_name(),
                chatCard.isUser_isOnline(),
                chatCard.getUser_pictrue()

        ));});

    }









    @Override
    public void disconnect() throws RemoteException {}




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
    @Override
    public void receivedMessage(Message message) throws RemoteException {

        ObservableList<ChatCardClient> chatCardClients = Cordinator.getContactList();
        ChatCardClient activeChat = null;

        for (ChatCardClient client : chatCardClients) {
            if (client.getChatId() == message.getChat_id()) {
                activeChat = client;
                System.out.println(client.getChatName());
             //   System.out.println(client.getUserId());
                System.out.println(client.getUserName());



                client.setMessageTime(message.getTime());
                client.setMessageId(message.getId());
                client.setMessageDesc(message.getDescription());
                break;
            }
        }


        if (activeChat != null) {

            ChatCardClient finalActiveChat = activeChat;

            System.out.println(activeChat.getUserName());
            //   System.out.println(client.getUserId());
            System.out.println(finalActiveChat.getUserName());

          Platform.runLater(() -> { chatCardClients.remove(finalActiveChat);
                chatCardClients.add(0, finalActiveChat);});
        }

    }



    }


