package com.chat.service.impl;

import com.chat.dao.impl.NotificationService;
import com.chat.dao.impl.UserNotificationService;
import com.chat.entity.Notification;
import com.chat.service.repository.NotificationRepository;

import java.sql.Timestamp;
import java.util.ArrayList;

public class NotificationImpl implements NotificationRepository {
    @Override
    public Notification createNotification( String desc, Timestamp time, int senderId, boolean isMessage, int chat_id) {
        Notification notification=new Notification(desc,time,senderId,isMessage,chat_id);
        NotificationService notificationService=new NotificationService();
        notification.setId(notificationService.createNotification(notification));
        return notification;
    }

    @Override
    public ArrayList<Notification> getAllNotification(int userId) {
       NotificationService notificationService=new NotificationService();
       return notificationService.getNotification(userId);
    }

    @Override
    public void UpdateNotificationState(int NotificationId, int userId) {
        UserNotificationService userNotificationService=new UserNotificationService();
        userNotificationService.updateStatus(userId,NotificationId);

    }
}
