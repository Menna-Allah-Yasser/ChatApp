package org.example.repository;

import com.chat.chatapp.entity.Message;
import org.example.entity.Message;

import java.rmi.Remote;
import java.util.List;

public interface MessageRepository extends Remote {

    List<Message> getMessages();
    boolean addMessage(Message message);

    boolean deleteMessage(int id);

    List<Message> getChatMessages(int chat_id);

    List<Message> getUserMessages(int user_id);

    boolean updateMessageDescription(int id , String newDescription);
}
