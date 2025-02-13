package com.chat.service.impl;


import com.chat.dao.impl.ChatService;
import com.chat.dao.impl.MessageService;
import com.chat.dao.impl.ParticipantService;

import com.chat.dao.impl.UserService;
import com.chat.dao.repository.ParticipantRepository;
import com.chat.db.DBConnectionManager;
import com.chat.entity.*;

import com.chat.entity.Chat;
import com.chat.entity.Message;
import com.chat.entity.Participant;

import com.chat.service.repository.ChatServerRepository;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatServerImpl implements ChatServerRepository {

    ParticipantService participantService = new ParticipantService();
    ChatService chatService  = new ChatService();

    UserService userService = new UserService();
    MessageService messageService = new MessageService();

    Connection connection = DBConnectionManager.getConnection();

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
    public List<ChatCard> getChatsForUser(int userId) {
        List<ChatCard> chatCards = new ArrayList<>();
        String query =
                "SELECT \tdistinct c.name AS chat_name ,\n" +
                        "\tc.chat_id ,\n" +
                        "\tm.message_id AS message_id , \n" +
                        "\tm.description ,\n" +
                        "\tm.time \n" +
                        "\tFROM particpant p , chat c , message m\n" +
                        "WHERE p.chat_id = c.chat_id\n" +
                        "AND m.chat_id = c.chat_id\n" +
                        "AND p.particpant_id = ?\n" +
                        "AND m.time = (\n" +
                        "             SELECT MAX(m2.time)\n" +
                        "             FROM message m2\n" +
                        "             WHERE m2.chat_id = c.chat_id)\n" +
                        "ORDER BY m.time ;\n" +
                        "\n";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                ChatCard chatCard = new ChatCard();

               /* Chat chat = new Chat(rs.getString(1));
                chat.setId(rs.getInt(2));*/

                chatCard.setChat_id(rs.getInt(2));
                chatCard.setChat_name(rs.getString(1));
                chatCard.setMessage_id(rs.getInt("message_id"));
                chatCard.setMessage_desc(rs.getString("description"));
                chatCard.setMessage_time(rs.getTimestamp("time").toLocalDateTime());

                if(participantService.countChatParticpantsByChatId(rs.getInt(2))==2){
                    // one to one chat so that we need to get second user

                    List<Integer> userIDs = chatService.getChatUsersIdByChatId(rs.getInt(2));

                    int secondUserID=-1;
                    for(int id : userIDs){
                        if(id != userId){
                            secondUserID = id;
                            break;
                        }
                    }

                    User user = userService.findUserById(secondUserID);
                    chatCard.setUser_Id(secondUserID);
                    chatCard.setUser_name(user.getName());
                    chatCard.setChat_name(user.getName());///////////////
                    chatCard.setUser_isOnline(user.getIsOnline());

                    byte[] pictureBytes = user.getPicture();
                    chatCard.setUser_pictrue(pictureBytes != null ? toBoxedBytes(pictureBytes) : null);


                }

                chatCards.add(chatCard);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return chatCards;
    }

    public byte[] toBoxedBytes(byte[] bytes) {
        byte[] boxed = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            boxed[i] = bytes[i];
        }
        return boxed;

    }

    public static void main(String[] args) {

        /*ChatServerImpl server = new ChatServerImpl();
        System.out.println(server.getUserChatsByUserId(1));*/


        ChatServerImpl repo = new ChatServerImpl();
        List<ChatCard> chatCards = repo.getChatsForUser(1);
        chatCards.forEach(System.out::println);


        ChatServerImpl server = new ChatServerImpl();


    }

}
