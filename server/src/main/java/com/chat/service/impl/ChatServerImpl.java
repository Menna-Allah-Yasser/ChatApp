package com.chat.service.impl;


import com.chat.dao.impl.ChatService;
import com.chat.dao.impl.MessageService;
import com.chat.dao.impl.ParticipantService;
import com.chat.entity.Chat;
import com.chat.entity.Message;
import com.chat.entity.Participant;
import com.chat.service.repository.ChatServerRepository;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatServerImpl implements ChatServerRepository {

    ParticipantService participantService = new ParticipantService();
    ChatService chatService  = new ChatService();
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
    //need function to return list of objects of type chat to render chat and group chat

    @Override
    public List<Chat> getUserChatsByUserId(int user_id) {
        List<Chat> chats = new ArrayList<>();
        List<Integer> ids = participantService.getAllChatsById(user_id);
        chats = chatService.getChatsById(ids);
        return chats;
    }
    @Override
    public ArrayList<Participant> getChatParticipants(int chat_id) {
        ArrayList<Participant> participants;
        participants = participantService.geParticpantByChat(chat_id);
        return participants;
    }

    @Override
    public Chat getChatById(int chatId) {
        return chatService.getChatById(chatId);
    }

    @Override
    public void addMessage(Message message) {
        messageService.addMessage(message);
    }

    public static void main(String[] args) {
        ChatServerImpl server = new ChatServerImpl();

    }

}
