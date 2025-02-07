package com.chat.service.repository;

import com.chat.entity.Chat;
import com.chat.entity.Message;

import java.util.List;
import java.util.Map;

public interface ChatServerRepository {

     Map<Chat, List<Message>> getChatsWithMessagesByUserId(int user_id);
}
