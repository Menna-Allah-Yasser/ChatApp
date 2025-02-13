package com.chat.dao.impl;

import com.chat.entity.Chat;
import com.chat.dao.repository.ChatRepository;
import com.chat.db.DBConnectionManager;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChatService implements ChatRepository {

    private String query;


    @Override
    public int addNewChat(Chat chat) {
        int chatId = -1;
        query = "INSERT INTO CHAT (NAME) VALUES (?)";
        int rowAffected = 0;
        try (Connection connection = DBConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, chat.getName());
            rowAffected = preparedStatement.executeUpdate();
            connection.commit();
            if (rowAffected > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        chatId = generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return chatId;
    }
    @Override
    public List<Chat> getChats() {
        List<Chat> chatList = new ArrayList<>();
        query = "SELECT * FROM CHAT";
        try(Connection connection=DBConnectionManager.getConnection();Statement statement = connection.createStatement() ) {
            ResultSet chats = statement.executeQuery(query);

            while(chats.next()){
                Chat chat = new Chat(chats.getString(2));
                chat.setId(chats.getInt(1));
                chatList.add(chat);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return chatList;
    }
    @Override
    public boolean updateChatName(int id, String newName) {

        int rowAffected = 0;
        query = "UPDATE CHAT SET Name = ? WHERE chat_id = ?";

        try(Connection connection=DBConnectionManager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            preparedStatement.setString(1 , newName);
            preparedStatement.setInt(2 , id);
            rowAffected = preparedStatement.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rowAffected > 0;
    }
    @Override
    public boolean isValidChatId(int chat_id) {
        query = "SELECT chat_id FROM Chat";
        try (Connection connection=DBConnectionManager.getConnection();Statement statement = connection.createStatement();){

            ResultSet ids = statement.executeQuery(query);
            while (ids.next()){
                if(ids.getInt(1) == chat_id){
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    @Override
    public Chat getChatById(int chatId) {

        query = "SELECT * FROM chat WHERE chat_id=?";
        Chat chat = null;

        try(Connection connection=DBConnectionManager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1 , chatId);
            ResultSet rs = preparedStatement.executeQuery();
            connection.commit();
            while (rs.next()){
                chat = new Chat(rs.getString(2));
                chat.setId(rs.getInt(1));
            }
            rs.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return chat;

    }

    @Override
    public List<Chat> getChatsById(List<Integer> chat_ids) {
        List<Chat> chats = new ArrayList<>();
        for(int i : chat_ids){
            chats.add(getChatById(i));
        }
        return chats;
    }

    public List<Integer> getChatUsersIdByChatId(int chat_id){
        List<Integer> userIDs = new ArrayList<>();
        query = "select chat_id , particpant_id from particpant where chat_id = ? ";

        try( Connection connection=DBConnectionManager.getConnection();PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1 , chat_id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
               userIDs.add(rs.getInt(2));
            }
            rs.close();
            connection.commit();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return userIDs;
    }
    public static void main(String[] args)
    {
        Chat chat = new Chat("");
        ChatService service = new ChatService();
        System.out.println(service.addNewChat(chat));
    }

}

