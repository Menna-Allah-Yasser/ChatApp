package com.chat.dao.repository;



import com.chat.entity.Chat;
import java.rmi.Remote;
import java.util.List;

public interface ChatRepository extends Remote {

    int addNewChat(Chat chat);

    List<Chat> getChats();

    boolean updateChatName(int id , String newName);

    boolean isValidChatId(int id);

    List<Chat> getChatsById(List<Integer> chat_ids);

    Chat getChatById(int chatId);

    List<Integer> getChatUsersIdByChatId(int chat_id);
}
