package com.chat.repository;

<<<<<<< HEAD
import java.util.List;

public interface UserRepository {

    int getIdByPhoneNumber(String PhoneNumber);
    List<Integer> getIDsByPhoneNumber(List<String> phoneNumbers);





=======

import com.chat.entity.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserRepository  extends Remote {


    public User findUserByPhoneNumber(String phoneNumber)   throws RemoteException;

    public void deleteUserByPhoneNumber(String phoneNumber) throws RemoteException;

    public  void UpdateUser(User userDTO ) throws  RemoteException;

    public  void addNewUser(User userDTO) throws RemoteException;

    public void updateStatus(int  userId ,String status) throws RemoteException ;

    public void updateChatbotEnabled(int  userId ,Boolean status) throws RemoteException;

    public  boolean authenticateUser(String phoneNumber, String password) throws RemoteException;

    public void updateOnline(int userId ,Boolean isOnline);
>>>>>>> 4442e0467e4dbefeb7648e138dff36f4fe5814aa

}
