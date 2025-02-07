package com.chat.service.repository;

import com.chat.entity.Notification;

import java.sql.Timestamp;
import java.util.ArrayList;

public interface NotificationRepository {
    public Notification createNotification( String desc, Timestamp time, int senderId, boolean isMessage, int chat_id);
    public ArrayList<Notification>getAllNotification(int userId);
    public void UpdateNotificationState(int NotificationId,int userId);

}
