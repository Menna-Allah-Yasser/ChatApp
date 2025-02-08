package com.chat.utils;

import com.chat.controller.BarController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Cordinator {
    private static ObservableList<Object> flist= FXCollections.observableArrayList();
    public static BarController barController;
    private static ObservableList<Object> nlist= FXCollections.observableArrayList();
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

    public static ObservableList<Object> getFriendRequestlist() {
        return flist;
    }
}
