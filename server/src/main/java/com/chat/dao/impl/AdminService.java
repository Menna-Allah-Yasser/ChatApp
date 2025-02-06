package com.chat.dao.impl;


import com.chat.dao.repository.AdminRepository;
import com.chat.db.DBConnectionManager;
import com.chat.entity.Admin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminService implements AdminRepository {

    @Override
    public boolean addAdmin(Admin admin) {
        String query = "INSERT INTO admin (password, email) VALUES(?, ?)";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, admin.getPassword());
            pstmt.setString(2, admin.getEmail());

            int numOfRowsEffected = pstmt.executeUpdate();
            return numOfRowsEffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error adding admin: " + e.getMessage());
        }
    }

    @Override
    public Admin getAdminById(int adminId) {
        String query = "SELECT * FROM admin WHERE admin_id = ?";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, adminId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Admin(
                        rs.getInt("admin_id"),
                        rs.getString("password"),
                        rs.getString("email")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving admin: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Admin> getAllAdmins() {
        String query = "SELECT * FROM admin";
        List<Admin> admins = new ArrayList<>();

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                admins.add(new Admin(
                        rs.getInt("admin_id"),
                        rs.getString("password"),
                        rs.getString("email")
                ));
            }
            return admins;
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving all admins: " + e.getMessage());
        }
    }

    @Override
    public boolean updateAdmin(Admin admin) {
        String query = "UPDATE admin SET password = ?, email = ? WHERE admin_id = ?";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, admin.getPassword());
            pstmt.setString(2, admin.getEmail());
            pstmt.setInt(3, admin.getAdminId());

            int numOfRowsEffected = pstmt.executeUpdate();
            return numOfRowsEffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating admin: " + e.getMessage());
        }
    }

    @Override
    public boolean deleteAdmin(int adminId) {
        String query = "DELETE FROM admin WHERE admin_id = ?";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, adminId);

            int numOfRowsEffected = pstmt.executeUpdate();
            return numOfRowsEffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting admin: " + e.getMessage());
        }
    }
}
