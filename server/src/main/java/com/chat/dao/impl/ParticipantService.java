package com.chat.dao.impl;

import com.chat.dao.repository.ParticipantRepository;
import com.chat.db.DBConnectionManager;
import com.chat.entity.Participant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParticipantService implements ParticipantRepository {
    @Override
    public Participant geParticpant(int id, int chat_id) {
        Participant p = null;
        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement stmt = conn
                     .prepareStatement("SELECT * FROM particpant WHERE particpant_id=? and chat_id=?")) {

            stmt.setInt(1, id);
            stmt.setInt(2, chat_id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())

                p = new Participant(rs.getInt("chat_id"), rs.getInt("particpant_id"),
                        Participant.State.valueOf(rs.getString("state")),
                        Participant.Category.valueOf(rs.getString("category")));
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }

    @Override
    public ArrayList<Participant> geParticpantByChat(int id) {
        ArrayList<Participant> arr = new ArrayList<>();
        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM particpant WHERE chat_id=?");) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            while (rs.next())
                arr.add(new Participant(rs.getInt("chat_id"), rs.getInt("particpant_id"),
                        Participant.State.valueOf(rs.getString("state")),
                        Participant.Category.valueOf(rs.getString("category"))));
            rs.close();
            return arr;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void createParticpant(Participant p) {
        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO particpant VALUES (?,?,?,?)");) {
            stmt.setInt(1, p.getChatId());
            stmt.setInt(2, p.getParticpantId());
            stmt.setString(4, p.getCategory().name());
            stmt.setString(3, p.getState().name());
            stmt.executeUpdate();
            System.out.println("added");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateParticpant(Participant p) {
        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE particpant SET state=?,category=? WHERE particpant_id=? and chat_id=?");) {

            stmt.setInt(4, p.getChatId());
            stmt.setInt(3, p.getParticpantId());
            stmt.setString(1, p.getState().name());
            stmt.setString(2, p.getCategory().name());
            stmt.executeUpdate();
            System.out.println(stmt);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteParticpant(Participant p) {
        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement stmt = conn
                     .prepareStatement("DELETE FROM particpant WHERE particpant_id=? and chat_id=?");) {

            stmt.setInt(1, p.getParticpantId());
            stmt.setInt(2, p.getChatId());
            stmt.executeUpdate();
            System.out.println("deleted");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // why would anyone need to get all prticpant from the data base
    @Override
    public ArrayList<Participant> geParticpants(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'geParticpants'");
    }

    @Override
    public List<Integer> getChatsIdUsingUserIdAndCategory(int user_id, Participant.Category category) {
        ArrayList<Integer> res = null;
        try (Connection c = DBConnectionManager.getConnection();
             PreparedStatement stmt = c
                     .prepareStatement("select chat_id from particpant where particpant_id=? and category=?;")) {
            res = new ArrayList<>();
            stmt.setInt(1, user_id);
            stmt.setString(2, category.name());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                res.add(rs.getInt(1));

            }

        } catch (Exception e) {
            System.out.println("problem in getChatsIdUsingUserIdAndCategory");

        }
        return res;
    }

    @Override
    public boolean addListOfParticipant(List<Participant> participants) {

        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement stmt = con.prepareStatement("INSERT INTO particpant VALUES (?,?,?,?)")) {
            for (Participant part : participants) {
                stmt.setInt(1, part.getChatId());
                stmt.setInt(2, part.getParticpantId());
                stmt.setString(3, part.getState().name());
                stmt.setString(4, part.getCategory().name());
                stmt.executeUpdate();
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
        return true;

    }


      @Override
    public List<Integer> getAllChatsById(int user_id) {

        ArrayList<Integer> res = null;
        try (Connection c = DBConnectionManager.getConnection();
             PreparedStatement stmt = c
                     .prepareStatement("select chat_id from particpant where particpant_id=?")) {
            res = new ArrayList<>();
            stmt.setInt(1, user_id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                res.add(rs.getInt(1));

            }

        } catch (Exception e) {
            System.out.println("problem in getAllChatsById");

        }
        return res;
    }

}

