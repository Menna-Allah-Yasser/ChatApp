package com.chat.repository;


import com.chat.entity.Message;
import java.rmi.Remote;
import java.util.List;

public interface MessageRepository extends Remote {

    List<Message> getMessages();
    boolean addMessage(Message message);

    boolean deleteMessage(int id);

    List<Message> getChatMessages(int chat_id); // add limit

    List<Message> getUserMessages(int user_id);

    boolean updateMessageDescription(int id , String newDescription);
}
