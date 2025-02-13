package com.chat.utils;

import com.chat.controller.BarController;

import com.chat.entity.ChatCardClient;

import com.chat.entity.Chat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Cordinator {


    //    private static ObservableList<Object> chats=FXCollections.observableArrayList();


//    private static ObservableList<Object> chats=FXCollections.observableArrayList();

    private static ObservableList<Object> flist= FXCollections.observableArrayList();
    public static BarController barController;
    private static ObservableList<Object> nlist= FXCollections.observableArrayList();
    private static ObservableList<Object> alist= FXCollections.observableArrayList();

    private static ObservableList<Object> list= FXCollections.observableArrayList();



//    private static ObservableList<Object> flist= FXCollections.observableArrayList();

  

    private static ObservableList<ChatCardClient> contactList = FXCollections.observableArrayList();

    private static ScheduledExecutorService scheduledExecutorService =
            Executors. newScheduledThreadPool(10);
    //make it synch or not ? if ii want to update it while it is opened in ui this means two thread accessing it i guess
    public static ObservableList<Object> getNotificationList()
    {
        return nlist;
    }
    //make it synch or not
    public static  ScheduledExecutorService getScheduledExecutorService() {
        return scheduledExecutorService;
    }
    public static ObservableList <ChatCardClient> getContactList(){
        return contactList;
    }

    public static ObservableList<Object> getFriendRequestlist() {
        return flist;
    }

    public static ObservableList<Object> getAlist() {
        return alist;
    }

    //    public static ObservableList<Object> getChats() {


    //    public static ObservableList<Object> getChats() {

//    public static ObservableList<Object> getChats() {


//        return chats;
//    }
//
    public static ObservableList<Object> getList() {
        return list;
    }


}

