package com.chat.dao.impl;

import com.chat.dao.repository.NotificationRepository;
import com.chat.db.DBConnectionManager;
import com.chat.entity.Notification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class NotificationService implements NotificationRepository {
    public int createNotification(Notification notification) {
        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement stmt = con.prepareStatement(
                     "INSERT INTO notifcation (describtion,time,senderId,isMessage,chatId) VALUES (?,?,?,?,?)",
                     Statement.RETURN_GENERATED_KEYS);
             PreparedStatement stmt3 = con.prepareStatement(
                     "SELECT sender_id FROM invitation where receiver_id=? and status ='ACCEPT'\n" + //
                             "UNION ALL\n" + //
                             "SELECT receiver_id FROM invitation where sender_id=? and status ='ACCEPT'");
             PreparedStatement stmt4 = con.prepareStatement(
                     "select particpant_id from particpant where chat_id=? and particpant_id != ?")) {
            stmt.setInt(3, notification.getSenderId());
            stmt.setString(1, notification.getDesc());
            stmt.setBoolean(4, notification.getIsMessage());
            stmt.setObject(2, notification.getTime());
            stmt.setInt(5, notification.getChat_id());

            int id = stmt.executeUpdate();
            ResultSet rrs = stmt.getGeneratedKeys();
            rrs.next();
            id = rrs.getInt(1);
            rrs.close();
            System.out.println(id);
            if (notification.getIsMessage()) {
                stmt4.setInt(1, notification.getChat_id());
                stmt4.setInt(2, notification.getSenderId());
                ResultSet rs = stmt4.executeQuery();
                UserNotificationService impl = new UserNotificationService();
                while (rs.next()) {
                    impl.createUserNottification(rs.getInt(1), id);

                }
                rs.close();
                return id;

            } else {
                stmt3.setInt(1, notification.getSenderId());
                stmt3.setInt(2, notification.getSenderId());
                ResultSet rs = stmt3.executeQuery();
                UserNotificationService impl = new UserNotificationService();
                while (rs.next()) {
                    impl.createUserNottification(rs.getInt(1), id);

                }
                rs.close();
                return id;

            }

            // get list of user friends and create user notification using their Id and
            // notification id
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        return -1;
    }

    @Override
    public ArrayList<Notification> getNotification(int receiver_id) {
        ArrayList<Notification> nots = new ArrayList<>();
        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement stmt = con.prepareStatement(
                     "select * from notifcation ,user_notifcation where notifcation.notifcation_id=user_notifcation.notifcation_id and user_id=? order by time desc")) {
            stmt.setInt(1, receiver_id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                nots.add(new Notification(rs.getInt("notifcation_id"), rs.getString("describtion"),
                        rs.getTimestamp("time"), rs.getInt("senderId"), rs.getBoolean("isMessage"),
                        rs.getInt("chatId")));

            }
            rs.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return nots;
    }

}
