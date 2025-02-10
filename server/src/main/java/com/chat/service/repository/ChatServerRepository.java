package com.chat.service.repository;

import com.chat.entity.Chat;
import com.chat.entity.Message;
import com.chat.entity.Participant;

import java.util.List;
import java.util.Map;

public interface ChatServerRepository {

     Map<Chat, List<Message>> getChatsWithMessagesByUserId(int user_id);

     List<Chat> getUserChatsByUserId(int user_id);
     public List<Participant> getChatParticipants(int chat_id);
     public Chat getChatById(int chatId);
}
