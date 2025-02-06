package com.chat.network;

import com.chat.entity.*;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerImpl extends UnicastRemoteObject implements ServerRepository {

    public static ConcurrentHashMap<Integer ,ClientRepository > clients ;

    private  static  ServerRepository server ;

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



    }

    @Override
    public void logout(int id) throws RemoteException {

    }

    @Override
    public void signUp(User user) throws RemoteException {

    }

    @Override
    public void updateStatus(int userId, String status) throws RemoteException {

    }

    @Override
    public void updateChatbotEnabled(int userId, Boolean status) throws RemoteException {

    }

    @Override
    public boolean authenticateUser(String phoneNumber, String password) throws RemoteException {
        return false;
    }

    @Override
    public void updateOnline(int userId, Boolean isOnline) throws RemoteException {

    }

    @Override
    public boolean updateUserInfo(User user) throws RemoteException {
        return false;
    }

    @Override
    public boolean updateUserImage(int userID, String phone, byte[] img) throws RemoteException {
        return false;
    }

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
        return null;
    }

    @Override
    public List<Invitation> getAllInvitation(int userId) throws RemoteException {
        return null;
    }

    @Override
    public User getUser(int userID) throws RemoteException {
        return null;
    }

    @Override
    public User getUser(String phoneNumber) throws RemoteException {
        return null;
    }

    @Override
    public Boolean sendFriendRequest(List<Invitation> addRequests) throws RemoteException {
        return null;
    }

    @Override
    public Boolean updateFriendsRequestStatus(Invitation Invitation) throws RemoteException {
        return null;
    }

    @Override
    public void sendMessage(int sender_id, int recevier_id, Message Message, int ChatId) throws RemoteException {

    }

    @Override
    public void createGroup(String Name, List<Integer> id) throws RemoteException {

    }

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

    }

    @Override
    public List<Announcement> getAllAnnouncememts() throws RemoteException {
        return null;
    }

    @Override
    public List<Notification> getAllNotifications() throws RemoteException {
        return null;
    }
}
