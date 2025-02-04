package com.chat.repository;

public interface UserNotificationRepository {

   boolean updateNotificationStatusByUserIdAndNotificationId(int user_id , int Notification_id);

}
