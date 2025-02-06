package com.chat.dao.impl;

import com.chat.db.DBConnectionManager;
import com.chat.entity.Message;
import com.chat.dao.repository.MessageRepository;
import com.chat.db.DBConnectionManager;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageService extends UnicastRemoteObject implements MessageRepository {

    private String query;
    private DBConnectionManager DBConnectionManager;
    private final Connection connection = DBConnectionManager.getConnection();

    public MessageService() throws RemoteException, SQLException {
    }

    @Override
    public List<Message> getMessages() {
        List<Message> messageList = new ArrayList<>();

        query = "SELECT * FROM Message";
        try(Statement statement = connection.createStatement() ) {
            ResultSet messages = statement.executeQuery(query);

            while(messages.next()){
                Message message = new Message(
                        messages.getString("description"),
                        messages.getTimestamp("time").toLocalDateTime(),
                        messages.getString("file_url"),
                        messages.getString("file_type"),
                        messages.getInt("chat_id"),
                        messages.getInt("user_id")
                );

                message.setId(messages.getInt("message_id"));
                messageList.add(message);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messageList;

    }

    @Override
    public boolean addMessage(Message message) {


        int rowAffected = 0;
        query = "INSERT INTO Message (description, time, file_url, file_type, chat_id, user_id) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){

            preparedStatement.setString(1 , message.getDescription());
            preparedStatement.setTimestamp(2, Timestamp.valueOf( message.getTime()));
            preparedStatement.setString(3 , message.getFile_url());
            preparedStatement.setString(4 , message.getFile_type());
            preparedStatement.setInt(5 , message.getChat_id());
            preparedStatement.setInt(6 , message.getUser_id());

            rowAffected = preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rowAffected > 0;
    }

    @Override
    public boolean deleteMessage(int id) {

        int rowAffected = 0;
        query = "DELETE FROM Message WHERE message_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1 , id);
            rowAffected = preparedStatement.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return rowAffected > 0;
    }

    @Override
    public List<Message> getChatMessages(int chat_id) {
        List<Message> messages = new ArrayList<>();
        query = "SELECT * FROM Message WHERE chat_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1 , chat_id);
            ResultSet set = preparedStatement.executeQuery();
            while (set.next()){
                Message message = new Message( set.getString("description"),
                        set.getTimestamp("time").toLocalDateTime(),
                        set.getString("file_url"),
                        set.getString("file_type"),
                        set.getInt("chat_id"),
                        set.getInt("user_id")
                );

                message.setId(set.getInt("message_id"));
                messages.add(message);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }

    @Override
    public List<Message> getUserMessages(int user_id) {
        List<Message> messages = new ArrayList<>();
        query = "SELECT * FROM Message WHERE user_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1 , user_id);
            ResultSet set = preparedStatement.executeQuery();
            while (set.next()){
                Message message = new Message( set.getString("description"),
                        set.getTimestamp("time").toLocalDateTime(),
                        set.getString("file_url"),
                        set.getString("file_type"),
                        set.getInt("chat_id"),
                        set.getInt("user_id")
                );

                message.setId(set.getInt("message_id"));
                messages.add(message);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }

    @Override
    public boolean updateMessageDescription(int id, String newDescription) {

        int rowAffected = 0;
        query = "UPDATE Message SET description = ? WHERE message_id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1 , newDescription);
            preparedStatement.setInt(2 , id);
            rowAffected = preparedStatement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rowAffected > 0;
    }

    public static void main(String[] args) {
       /* Message message = new Message("hi mnmn", LocalDateTime.now(),1 , 1 );
        MessageService messageService = new MessageService();
        System.out.println(messageService.addMessage(message));*/

      /*  MessageService messageService = new MessageService();
        System.out.println(messageService.deleteMessage(3));*/

       /* MessageService messageService = new MessageService();
        System.out.println(messageService.getMessages());*/


       /* MessageService messageService = new MessageService();
        System.out.println(messageService.updateMessageDescription(1 , "Hi mo"));*/

       /* MessageService messageService = new MessageService();
        System.out.println(messageService.getChatMessages(9));*/
    }

}