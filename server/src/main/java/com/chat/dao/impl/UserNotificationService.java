package com.chat.dao.impl;

import com.chat.dao.repository.UserNotificationRepository;
import com.chat.db.DBConnectionManager;
import com.chat.entity.Notification;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UserNotificationService implements UserNotificationRepository {

    @Override
    public boolean deleteUserNotifction(int user_id, int Notification_id) {
        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement stmt = conn
                     .prepareStatement("DELETE FROM user_notifcation WHERE user_id=? and notifcation_id=?");) {

            stmt.setInt(1, user_id);
            stmt.setInt(2, Notification_id);

            stmt.executeUpdate();
            System.out.println("deleted");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean updateStatus(int user_id, int Notification_id) {
        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE user_notifcation SET status=? WHERE user_id=? and notifcation_id=?");) {

            stmt.setString(1, Notification.status.READ.name());
            stmt.setInt(2, user_id);
            stmt.setInt(3, Notification_id);
            stmt.executeUpdate();
            System.out.println("updated");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean createUserNottification(int user_id, int Notification_id) {

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO user_notifcation VALUES (?,?,?)");) {
            stmt.setInt(1, user_id);
            stmt.setInt(2, Notification_id);
            stmt.setString(4, Notification.status.UNREAD.name());

            if (stmt.executeUpdate() != 0)
                return false;
            System.out.println("added");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;

    }

}
