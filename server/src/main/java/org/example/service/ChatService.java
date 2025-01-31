package org.example.service;

import org.example.db.DBConnectionManager;
import org.example.entity.Chat;
import org.example.repository.ChatRepository;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChatService extends UnicastRemoteObject implements ChatRepository {

    private String query;
    private Connection connection = DBConnectionManager.getConnection();

    public ChatService() throws RemoteException{
    }
    @Override
    public boolean addNewChat(Chat chat) {
        query = "INSERT INTO CHAT (NAME) VALUES (?)";
        int rowAffected = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);){
            preparedStatement.setString(1 , chat.getName());
            rowAffected = preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return rowAffected > 0;
    }
    @Override
    public List<Chat> getChats() {
        List<Chat> chatList = new ArrayList<>();
        query = "SELECT * FROM CHAT";
        try(Statement statement = connection.createStatement() ) {
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

        try( PreparedStatement preparedStatement = connection.prepareStatement(query);) {
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
        try {
            Statement statement = connection.createStatement();
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

    public static void main(String[] args) {
      /*  Chat chat = new Chat("iti");
        ChatService chatService = new ChatService();
        System.out.println(chatService.addNewChat(chat));*/


       /* ChatService chatService = new ChatService();
        System.out.println(chatService.getChats());*/

       /* ChatService chatService = new ChatService();
        System.out.println(chatService.updateChatName(2 , "MEC"));*/

    }
}