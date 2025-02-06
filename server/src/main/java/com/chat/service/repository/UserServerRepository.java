package com.chat.service.repository;

import com.chat.entity.User;

import java.util.ArrayList;

public interface UserServerRepository {


    public User findUserByPhoneNumber(String phoneNumber)   ;
    public void deleteUserByPhoneNumber(String phoneNumber) ;

    public User findUserById(int userId)   ;

    public ArrayList<User> getAllUsers(ArrayList<Integer> usersId);

    public  void updateUser(User userDTO ) ;

    public  void addNewUser(User userDTO) ;

    public void updateStatus(int  userId ,String status)  ;

    public void updateChatbotEnabled(int  userId ,Boolean status) ;

    public  boolean authenticateUser(String phoneNumber, String password) ;

    public void updateOnline(int userId ,Boolean isOnline) ;

    public  ArrayList<User> getFriendsUser(int id);

    public void updateUserImage(int userID, byte[] img);



}
