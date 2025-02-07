package com.chat.network;

import com.chat.entity.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;

public interface ServerRepository  extends Remote {

    public void login(int id, ClientRepository callback)throws RemoteException;
    //call fun update status and add user to server

    public void logout(int id)throws RemoteException;
    //call fun update status and delete user from server

    public void  signUp(User user) throws RemoteException;

    public void updateStatus(int  userId ,String status) throws RemoteException;

    public void updateChatbotEnabled(int  userId ,Boolean status) throws RemoteException;

    public  boolean authenticateUser(String phoneNumber, String password)throws RemoteException;

    public void updateOnline(int userId ,Boolean isOnline) throws RemoteException;

    public void updateUserInfo(User user)throws RemoteException;
    public void updateUserImage(int userID, byte[] img) throws RemoteException;
  //  public boolean updateUserPassword(int userID, String password) throws RemoteException ;


    public User getUser(int userID)  throws RemoteException;
    public User getUser(String phoneNumber) throws RemoteException;




    public HashMap<Chat,List<Message>>  getAllChats(int id) throws RemoteException;

    public List<Message> getChatMessages(int chatID) throws RemoteException ;

    public List<User> getAllFrirnds(int userId) throws RemoteException;

    public List<Invitation> getAllInvitation(int userId) throws RemoteException;

    public Boolean sendFriendRequest( List<Invitation> addRequests ) throws RemoteException;
    public  Boolean updateFriendsRequestStatus (Invitation Invitation) throws RemoteException;


    public void sendMessage(int sender_id , int recevier_id , Message Message, int ChatId) throws RemoteException;

    public void createGroup(String Name , List<Integer> id) throws RemoteException;

    public int createChat(Chat chat)  throws RemoteException;
    public Chat getChat(int chatID) throws RemoteException;
    //public List<User> getGroupMembers(int chatId) throws RemoteException;




    public void sendInvitation(int user_id, List<User> users)throws RemoteException;


    public List<Announcement> getAllAnnouncememts() throws RemoteException;

    public List<Notification> getAllNotifications(int userId)throws RemoteException;
    public void updateNotification(int NotificationId, int userId) throws RemoteException;






}
