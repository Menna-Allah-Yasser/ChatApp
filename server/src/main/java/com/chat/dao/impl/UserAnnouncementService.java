package com.chat.dao.impl;


import com.chat.dao.repository.UserAnnouncementRepository;
import com.chat.db.DBConnectionManager;
import com.chat.entity.UserAnnouncement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserAnnouncementService implements UserAnnouncementRepository {

    @Override
    public boolean addUserAnnouncement(UserAnnouncement userAnnouncement) {
        String query = "INSERT INTO user_announcement (user_id, announcement_id) VALUES(?, ?)";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userAnnouncement.getUserId());
            pstmt.setInt(2, userAnnouncement.getAnnouncementId());

            int numOfRowsAffected = pstmt.executeUpdate();
            return numOfRowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error adding user announcement: " + e.getMessage());
        }
    }

    @Override
    public List<UserAnnouncement> getAnnouncementsByUserId(int userId) {
        String query = "SELECT * FROM user_announcement WHERE user_id = ?";
        List<UserAnnouncement> userAnnouncements = new ArrayList<>();

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                userAnnouncements.add(new UserAnnouncement(
                        rs.getInt("user_id"),
                        rs.getInt("announcement_id")
                ));
            }
            return userAnnouncements;
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving user announcements: " + e.getMessage());
        }
    }

    @Override
    public boolean deleteUserAnnouncement(int userId, int announcementId) {
        String query = "DELETE FROM user_announcement WHERE user_id = ? AND announcement_id = ?";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, announcementId);

            int numOfRowsAffected = pstmt.executeUpdate();
            return numOfRowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting user announcement: " + e.getMessage());
        }
    }

    @Override
    public boolean addListOfUserAnnouncements(List<UserAnnouncement> userAnnouncements) {
        String query = "INSERT INTO user_announcement (user_id, announcement_id) VALUES(?, ?)";
        Connection temp = null;
        try (Connection conn = DBConnectionManager.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                for (UserAnnouncement userAnnouncement : userAnnouncements) {
                    pstmt.setInt(1, userAnnouncement.getUserId());
                    pstmt.setInt(2, userAnnouncement.getAnnouncementId());
                    pstmt.addBatch();
                }
                int[] numOfRowsAffected = pstmt.executeBatch();

                conn.commit();
                return numOfRowsAffected.length == userAnnouncements.size();
            } catch (SQLException e) {
                conn.rollback();
                throw new RuntimeException("Error while adding user announcements: " + e.getMessage(), e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error with database connection or transaction: " + e.getMessage(), e);
        }
    }
}
