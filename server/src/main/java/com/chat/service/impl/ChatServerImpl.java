package com.chat.service.impl;


import com.chat.dao.impl.ChatService;
import com.chat.dao.impl.MessageService;
import com.chat.dao.impl.ParticipantService;
import com.chat.entity.Chat;
import com.chat.entity.Message;
import com.chat.service.repository.ChatServerRepository;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatServerImpl implements ChatServerRepository {

    ParticipantService participantService = new ParticipantService();
    ChatService chatService  = new ChatService();;
    MessageService messageService = new MessageService();

    @Override
    public Map<Chat, List<Message>> getChatsWithMessagesByUserId(int user_id) {

        Map<Chat , List<Message>> mp = new HashMap<>();
        List<Integer> chat_ids = participantService.getAllChatsById(user_id);
        List<Chat> chats = chatService.getChatsById(chat_ids);
        for(Chat chat : chats){
            mp.put(chat ,messageService.getChatMessages(chat.getId()));
        }

        return mp;
    }

    public static void main(String[] args) {
        ChatServerImpl server = new ChatServerImpl();
        System.out.println(server.getChatsWithMessagesByUserId(1));
    }
}
