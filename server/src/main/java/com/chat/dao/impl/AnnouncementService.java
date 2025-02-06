package com.chat.dao.impl;


import com.chat.dao.repository.AnnouncementRepository;
import com.chat.db.DBConnectionManager;
import com.chat.entity.Announcement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnnouncementService implements AnnouncementRepository {

    @Override
    public boolean addAnnouncement(Announcement announcement) {
        String query = "INSERT INTO announcement (announcement_id, description, admin_id) VALUES(?, ?, ?)";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, announcement.getAnnouncementId());
            pstmt.setString(2, announcement.getDescription());
            pstmt.setInt(3, announcement.getAdminId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error adding announcement: " + e.getMessage());
        }
    }

    @Override
    public Announcement getAnnouncementById(int id) {
        String query = "SELECT * FROM announcement WHERE announcement_id = ?";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Announcement(
                        rs.getInt("announcement_id"),
                        rs.getString("description"),
                        rs.getInt("admin_id")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving announcement: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Announcement> getAllAnnouncements() {
        String query = "SELECT * FROM announcement";
        List<Announcement> announcements = new ArrayList<>();

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                announcements.add(new Announcement(
                        rs.getInt("announcement_id"),
                        rs.getString("description"),
                        rs.getInt("admin_id")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving all announcements: " + e.getMessage());
        }
        return announcements;
    }

    @Override
    public boolean updateAnnouncement(Announcement announcement) {
        String query = "UPDATE announcement SET description = ?, admin_id = ? WHERE announcement_id = ?";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, announcement.getDescription());
            pstmt.setInt(2, announcement.getAdminId());
            pstmt.setInt(3, announcement.getAnnouncementId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating announcement: " + e.getMessage());
        }
    }

    @Override
    public boolean deleteAnnouncement(int id) {
        String query = "DELETE FROM announcement WHERE announcement_id = ?";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting announcement: " + e.getMessage());
        }
    }
}
