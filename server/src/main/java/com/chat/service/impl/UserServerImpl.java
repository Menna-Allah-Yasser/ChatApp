package com.chat.service.impl;


import com.chat.dao.impl.UserService;
import com.chat.entity.User;
import com.chat.network.ServerImpl;
import com.chat.service.repository.UserServerRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class UserServerImpl implements UserServerRepository {

    UserService userService;


    private static UserServerImpl instance;
    InvitationServerImpl invitationServer;

    private UserServerImpl() {
        this.userService = new UserService();
        this.invitationServer = InvitationServerImpl.getInvitationServerImp();
    }

    public static synchronized UserServerImpl getUserService() {
        if (instance == null) {
            instance = new UserServerImpl();
        }
        return instance;
    }

    @Override
    public User findUserByPhoneNumber(String phoneNumber) {
        return userService.findUserByPhoneNumber(phoneNumber);
    }

    @Override
    public void deleteUserByPhoneNumber(String phoneNumber) {

        userService.deleteUserByPhoneNumber(phoneNumber);

    }

    @Override
    public User findUserById(int userId) {
        return userService.findUserById(userId);
    }

    @Override
    public ArrayList<User> getAllUsers(ArrayList<Integer> usersId) {

        return userService.getAllUsers(usersId);
    }

    @Override
    public void updateUser(User userDTO) {

        userService.updateUser(userDTO);

    }

    @Override
    public void addNewUser(User userDTO) {

        userService.addNewUser(userDTO);

    }

    @Override
    public void updateStatus(int userId, String status) {

        userService.updateStatus(userId, status);
    }

    @Override
    public void updateChatbotEnabled(int userId, Boolean status) {

        userService.updateChatbotEnabled(userId,status);
    }

    @Override
    public boolean authenticateUser(String phoneNumber, String password) {
        return userService.authenticateUser(phoneNumber ,password);
    }

    @Override
    public void updateOnline(int userId, Boolean isOnline) {

        userService.updateOnline(userId, isOnline);
    }

    @Override
    public ArrayList<User> getFriendsUser(int id) {

      List<Integer> usersid=  invitationServer.getUserFriends(id);

        return userService.getAllUsers((ArrayList<Integer>) usersid);

    }

    @Override
    public void updateUserImage(int userID, byte[] img) {
        userService.updateUserImage(userID,img);
    }

    public static void main(String[] args) {
        UserServerImpl server1 = new UserServerImpl();

        ArrayList<User> users =  server1.getFriendsUser(4);

        for (User user:users)
        {
            System.out.println(user);
        }
    }
}
