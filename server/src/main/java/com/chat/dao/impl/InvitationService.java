package com.chat.dao.impl;


import com.chat.dao.repository.InvitationRepository;
import com.chat.db.DBConnectionManager;
import com.chat.entity.InvStatus;
import com.chat.entity.Invitation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvitationService implements InvitationRepository {

    @Override
    public boolean addInvitation(Invitation invitation) {
        String query = "INSERT INTO invitation (sender_id, receiver_id, status) VALUES(?, ?, ?)";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, invitation.getSenderId());
            pstmt.setInt(2, invitation.getReceiverId());
            pstmt.setString(3, invitation.getStatus().name());

            int numOfRowsEffected = pstmt.executeUpdate();
            conn.commit();
            return numOfRowsEffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Integer> getFriends(int userId) {
        String query = "SELECT sender_id FROM invitation WHERE receiver_id = ? AND status = 'ACCEPT' " +
                "UNION " +
                "SELECT receiver_id FROM invitation WHERE sender_id = ? AND status = 'ACCEPT'";

        List<Integer> friends = new ArrayList<>();

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, userId);
            ResultSet rs = pstmt.executeQuery();
            conn.commit();
            while (rs.next()) {
                friends.add(rs.getInt(1));
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error while fetching friends", e);
        }
        return friends;
    }


    @Override
    public boolean deleteInvitation(int senderId, int receiverId) {
        String query = "DELETE FROM invitation WHERE sender_id = ? AND receiver_id = ?";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, senderId);
            pstmt.setInt(2, receiverId);

            int numOfRowsEffected = pstmt.executeUpdate();
            conn.commit();
            return numOfRowsEffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Invitation> getAllInvitationsByStatus(int receiverId, InvStatus status) {
        String query = "SELECT sender_id, receiver_id , status FROM invitation WHERE status = ? AND receiver_id = ?";
        List<Invitation> invitations = new ArrayList<>();

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, status.name());
            pstmt.setInt(2, receiverId);
            ResultSet rs = pstmt.executeQuery();
            conn.commit();
            while (rs.next()) {
                Invitation invitation = new Invitation();
                invitation.setSenderId(rs.getInt("sender_id"));
                invitation.setReceiverId(rs.getInt("receiver_id"));  // Fix here: should be receiver_id, not sender_id
                invitation.setStatus(InvStatus.valueOf(rs.getString("status")));
                invitations.add(invitation);
            }
            rs.close();
            // sender_id recevier status
            // 1           2       accept
            // 2           3       accept
            return invitations;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public boolean addListOfInvitation(List<Invitation> invitations) {
        String query = "INSERT INTO invitation (sender_id, receiver_id, status) VALUES(?, ?, ?)";

        try (Connection conn = DBConnectionManager.getConnection()) {


            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                for (Invitation invitation : invitations) {
                    pstmt.setInt(1, invitation.getSenderId());
                    pstmt.setInt(2, invitation.getReceiverId());
                    pstmt.setString(3, invitation.getStatus().name());
                    pstmt.addBatch();
                }
                int[] numOfRowsEffected = pstmt.executeBatch();
                conn.commit();

                return numOfRowsEffected.length == invitations.size();
            } catch (SQLException e) {
                conn.rollback();
                throw new RuntimeException("Error while adding invitations: " + e.getMessage(), e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error with database connection or transaction: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Invitation> getAllInvitationsByReceiverId(int receiverId) {
        String query = "SELECT sender_id, receiver_id, status FROM invitation WHERE receiver_id = ?";
        List<Invitation> invitations = new ArrayList<>();

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, receiverId);
            ResultSet rs = pstmt.executeQuery();
    conn.commit();
            while (rs.next()) {
                Invitation invitationDTO = new Invitation();
                invitationDTO.setSenderId(rs.getInt("sender_id"));
                invitationDTO.setReceiverId(rs.getInt("receiver_id"));
                invitationDTO.setStatus(InvStatus.valueOf(rs.getString("status")));
                invitations.add(invitationDTO);
            }
            rs.close();
            return invitations;
        } catch (SQLException e) {
            throw new RuntimeException("Error while fetching invitations", e);
        }
    }

    @Override
    public boolean updateStatus(Invitation newInv) {
        String query = "UPDATE invitation SET status = ? WHERE sender_id = ? AND receiver_id = ?";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, newInv.getStatus().name());

            pstmt.setInt(2, newInv.getSenderId());
            pstmt.setInt(3, newInv.getReceiverId());

            int numOfRowsEffected = pstmt.executeUpdate();
            conn.commit();
            return numOfRowsEffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error while updating invitation status", e);
        }
    }

    private boolean checkUserExists(Connection con, int userId) {
        String sql = "SELECT 1 FROM user WHERE user_id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            con.commit();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

}

