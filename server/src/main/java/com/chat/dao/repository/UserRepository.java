package com.chat.dao.repository;

import java.util.List;
import com.chat.entity.User;

import java.rmi.RemoteException;

public interface UserRepository {

    int getIdByPhoneNumber(String PhoneNumber);
    List<Integer> getIDsByPhoneNumber(List<String> phoneNumbers);

    public User findUserByPhoneNumber(String phoneNumber)  throws RemoteException;

    public void deleteUserByPhoneNumber(String phoneNumber) throws RemoteException;

    public  void UpdateUser(User userDTO ) throws  RemoteException;

    public  void addNewUser(User userDTO) throws RemoteException;

    public void updateStatus(int  userId ,String status) throws RemoteException ;

    public void updateChatbotEnabled(int  userId ,Boolean status) throws RemoteException;

    public  boolean authenticateUser(String phoneNumber, String password) throws RemoteException;

    public void updateOnline(int userId ,Boolean isOnline);

}
