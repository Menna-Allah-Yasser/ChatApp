package com.chat.service.impl;


import com.chat.dao.impl.InvitationService;
import com.chat.dao.impl.UserService;
import com.chat.entity.User;
import com.chat.service.repository.UserServerRepository;

import java.util.ArrayList;

public class UserServerImpl implements UserServerRepository {

    UserService userService;

    InvitationService invitationService ;
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
        return null;
    }
}
