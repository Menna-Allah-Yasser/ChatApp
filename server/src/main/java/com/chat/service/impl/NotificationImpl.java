package com.chat.service.impl;

import com.chat.entity.Notification;
import com.chat.service.repository.NotificationRepository;

import java.sql.Timestamp;
import java.util.ArrayList;

public class NotificationImpl implements NotificationRepository {
    @Override
    public Notification createNotification(int Id, String desc, Timestamp time, int senderId, boolean isMessage, int chat_id) {
        return null;
    }

    @Override
    public ArrayList<Notification> getAllNotification(int userId) {
        return null;
    }

    @Override
    public void UpdateNotificationState(int NotificationId, int userId) {

    }
}
