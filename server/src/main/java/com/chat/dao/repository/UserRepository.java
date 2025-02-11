package com.chat.dao.repository;

import java.util.ArrayList;
import java.util.List;
import com.chat.entity.User;

import java.rmi.RemoteException;

public interface UserRepository {


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

    public void updateUserImage(int userID,  byte[] img);

    public List<User> getAllUsers();
}
