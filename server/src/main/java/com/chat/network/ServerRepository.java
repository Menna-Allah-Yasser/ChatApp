package com.chat.network;

import com.chat.entity.*;

import java.util.HashMap;
import java.util.List;

public interface ServerRepository {

    public void login(int id, ClientRepository callback);
    //call fun update status and add user to server

    public void logout(int id,ClientRepository callback);
    //call fun update status and delete user from server

    public void  signUp(User user);

    public boolean checkAuthontication(String phoneNumber , String password);

    public void sendMessage(int sender_id , int recevier_id , Message Message);

    public void sendMessagetoGroup(int sender_id , int recevier_id , Message Message);

    public void sendNotification(int sender_id , int recevier_id , Notification notification, String type);

    public void sendInvitation(int user_id, List<User> users);

    public void createGroup(String Name , List<Integer> id);

    public List<User> getAllFrirnds();

    public List<Invitation> getAllInvitation();

    public HashMap<Chat,List<Message>>  getAllChats(int id);

    public List<Notification> getAllNotifications();




}
