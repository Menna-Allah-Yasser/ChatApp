package com.chat.dao.repository;

public interface UserNotificationRepository {
    boolean deleteUserNotifction(int user_id, int Notification_id);

    boolean updateStatus(int user_id, int Notification_id);

    boolean createUserNottification(int user_id, int Notification_id);
}
