package com.chat.dao.repository;

import com.chat.entity.Notification;

import java.util.ArrayList;

public interface NotificationRepository {
    public int createNotification(Notification notification) ;

    public ArrayList<Notification> getNotification(int receiver_id) ;
}
