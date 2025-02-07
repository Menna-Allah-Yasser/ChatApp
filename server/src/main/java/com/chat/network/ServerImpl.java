package com.chat.network;

import com.chat.entity.*;
import com.chat.service.impl.InvitationServerImpl;
import com.chat.service.impl.NotificationImpl;
import com.chat.service.impl.UserServerImpl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ServerImpl extends UnicastRemoteObject implements ServerRepository {

    public static ConcurrentHashMap<Integer ,ClientRepository > clients ;

    private  static  ServerRepository server ;

    private  UserServerImpl userServer ;

   private NotificationImpl notificationServer;


    private ServerImpl() throws RemoteException {

    }

    public static ServerRepository getServer() {
        try {
            if (server == null)
                server = new ServerImpl();
            return server;
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public static ConcurrentHashMap<Integer, ClientRepository> getOnlineClients() {
        return clients;
    }


    @Override
    public void login(int id, ClientRepository callback) throws RemoteException {

        userServer= UserServerImpl.getUserService();
        notificationServer= NotificationImpl.getNotificationImpl();
        userServer.getUserService().updateOnline(id, true);

        clients.put(id , callback);

        List<User> friends =  userServer.getUserService().getFriendsUser(id);
        Notification notification = notificationServer.createNotification("went onLine", Timestamp.valueOf(LocalDateTime.now()),id, false ,0);
        for (User friend :friends)
      {
          if (clients.containsKey(friend.getUserId()))
          {

               ClientRepository clientRepository =clients.get(friend.getUserId());

               clientRepository.getNotification(notification);


          }



      }




    }

    @Override
    public void logout(int id) throws RemoteException {

        userServer= UserServerImpl.getUserService();
        notificationServer= NotificationImpl.getNotificationImpl();
        userServer.getUserService().updateOnline(id, false);

        clients.remove(id);

        List<User> friends =  userServer.getUserService().getFriendsUser(id);
        Notification notification = notificationServer.createNotification("went offLine", Timestamp.valueOf(LocalDateTime.now()),id, false ,0);
        for (User friend :friends)
        {
            if (clients.containsKey(friend.getUserId()))
            {
                ClientRepository clientRepository =clients.get(friend.getUserId());

                clientRepository.getNotification(notification);


            }



        }




    }

    @Override
    public void signUp(User user) throws RemoteException {

        userServer= UserServerImpl.getUserService();

        userServer.getUserService().addNewUser(user);

    }

    @Override
    public void updateStatus(int userId, String status) throws RemoteException {
        userServer= UserServerImpl.getUserService();
        userServer.updateStatus(userId ,status);
    }

    @Override
    public void updateChatbotEnabled(int userId, Boolean status) throws RemoteException {
        userServer= UserServerImpl.getUserService();
        userServer.updateChatbotEnabled(userId,status);
    }

    @Override
    public boolean authenticateUser(String phoneNumber, String password) throws RemoteException {
        userServer= UserServerImpl.getUserService();
        return userServer.authenticateUser(phoneNumber ,password) ;
    }

    @Override
    public void updateOnline(int userId, Boolean isOnline) throws RemoteException {
        userServer= UserServerImpl.getUserService();
        userServer.updateOnline(userId, isOnline);
    }

    @Override
    public void updateUserInfo(User user) throws RemoteException {
        userServer= UserServerImpl.getUserService();
        userServer.updateUser(user);
    }

    @Override
    public void updateUserImage(int userID, byte[] img) throws RemoteException {
        userServer= UserServerImpl.getUserService();
        userServer.updateUserImage(userID, img);
    }
    //we need this unction as two functions one to get the chats for a user and another one to get messages in one chat
    @Override
    public HashMap<Chat, List<Message>> getAllChats(int id) throws RemoteException {
        return null;
    }

    @Override
    public List<Message> getChatMessages(int chatID) throws RemoteException {
        return null;
    }

    @Override

    public List<User> getAllFrirnds(int userId) throws RemoteException {
        userServer= UserServerImpl.getUserService();

        return userServer.getFriendsUser(userId);

    }

    @Override
    public List<Invitation> getAllInvitation(int userId) throws RemoteException {
        return InvitationServerImpl.getInvitationServerImp().getUserFriendRequests(userId);
    }

    @Override
    public User getUser(int userID) throws RemoteException {
        userServer= UserServerImpl.getUserService();
        return userServer.findUserById(userID);
    }

    @Override
    public User getUser(String phoneNumber) throws RemoteException {
        userServer= UserServerImpl.getUserService();
        return userServer.findUserByPhoneNumber(phoneNumber);
    }

    @Override
    public Boolean sendFriendRequest(List<Invitation> addRequests) throws RemoteException {
        return InvitationServerImpl.getInvitationServerImp().sendFriendRequest(addRequests);
    }

    @Override
    public Boolean updateFriendsRequestStatus(Invitation invitation) throws RemoteException {
        return InvitationServerImpl.getInvitationServerImp().updateFriendsRequestStatus(invitation);
    }

    @Override
    public void sendMessage(int sender_id, int recevier_id, Message Message, int ChatId) throws RemoteException {

    }

    @Override
    public void createGroup(String Name, List<Integer> id) throws RemoteException {

    }
    //need service in the chat server impl to create chat
    @Override
    public int createChat(Chat chat) throws RemoteException {
        return 0;
    }

    @Override
    public Chat getChat(int chatID) throws RemoteException {
        return null;
    }

    @Override
    public void sendNotification(int sender_id, int recevier_id, Notification notification, String type) throws RemoteException {

    }

    @Override
    public void sendInvitation(int user_id, List<User> users) throws RemoteException {
        InvitationServerImpl invitationServer= InvitationServerImpl.getInvitationServerImpl();
        invitationServer.sendFriendRequest(user_id,users);
    }

    @Override
    public List<Announcement> getAllAnnouncememts() throws RemoteException {
        return null;
    }

    @Override
    public List<Notification> getAllNotifications(int userId) throws RemoteException {
        NotificationImpl notificationImpl=NotificationImpl.getNotificationImpl();
        return notificationImpl.getAllNotification(userId);
    }
}
