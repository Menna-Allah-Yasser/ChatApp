package com.chat.repository;



import com.chat.entity.Chat;
import java.rmi.Remote;
import java.util.List;

public interface ChatRepository extends Remote {

    boolean addNewChat(Chat chat);

    List<Chat> getChats();

    boolean updateChatName(int id , String newName);

    boolean isValidChatId(int id);


}
