package com.chat.network;

import com.chat.dao.impl.ChatService;

import com.chat.dao.impl.InvitationService;
import com.chat.dao.impl.ParticipantService;
import com.chat.dao.repository.ChatRepository;
import com.chat.dao.repository.MessageRepository;
import com.chat.dao.repository.ParticipantRepository;

import com.chat.dao.impl.MessageService;

import com.chat.entity.*;
import com.chat.service.impl.ChatBotServer;
import com.chat.service.impl.ChatServerImpl;
import com.chat.service.impl.InvitationServerImpl;
import com.chat.service.impl.NotificationImpl;
import com.chat.service.impl.UserServerImpl;
import com.chat.service.repository.ChatServerRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.Arrays;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ServerImpl extends UnicastRemoteObject implements ServerRepository {

    public static ConcurrentHashMap<Integer ,ClientRepository > clients ;

    private  static  ServerRepository server ;

    private  UserServerImpl userServer ;
   private NotificationImpl notificationServer;

    private ServerImpl() throws RemoteException {
        clients = new ConcurrentHashMap<>();
    }

    public static ServerRepository getServer() {
        try {
            if (server == null)
                server = new ServerImpl();
            return server;
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public static ConcurrentHashMap<Integer, ClientRepository> getOnlineClients() {
        return clients;
    }

    @Override
    public void login(int id, ClientRepository callback) throws RemoteException {
        userServer= UserServerImpl.getUserService();
        notificationServer= NotificationImpl.getNotificationImpl();
        userServer.getUserService().updateOnline(id, true);
        clients.put(id , callback);
        List<User> friends =  userServer.getUserService().getFriendsUser(id);
        Notification notification = notificationServer.createNotification("went onLine", Timestamp.valueOf(LocalDateTime.now()),id, false ,6);
        System.out.println("jhfjhfcghcgkcjhcghcgh");
        System.out.println(notification);
        System.out.println(friends);
        System.out.println(friends.size());
        if(friends!=null)
        {   for (User friend :friends)
      {
          if (clients.containsKey(friend.getUserId()))
          {

               ClientRepository clientRepository =clients.get(friend.getUserId());
               clientRepository.getNotification(notification);
               clientRepository.friendLoggedIn(id);
          }
      }}
    }

    @Override
    public void logout(int id) throws RemoteException {

        userServer= UserServerImpl.getUserService();
        notificationServer= NotificationImpl.getNotificationImpl();
        userServer.getUserService().updateOnline(id, false);
        clients.remove(id);
        List<User> friends =  userServer.getUserService().getFriendsUser(id);
        Notification notification = notificationServer.createNotification("went offLine", Timestamp.valueOf(LocalDateTime.now()),id, false ,6);
      if(friends != null)  for (User friend :friends)
        {
            if (clients.containsKey(friend.getUserId()))
            {
                ClientRepository clientRepository =clients.get(friend.getUserId());
                clientRepository.getNotification(notification);

                clientRepository.friendLoggedOut(id);

            }
        }
    }

    @Override
    public void signUp(User user) throws RemoteException {

        userServer= UserServerImpl.getUserService();

        userServer.getUserService().addNewUser(user);

    }

    @Override
    public void updateStatus(int userId, String status) throws RemoteException {
        userServer= UserServerImpl.getUserService();
        userServer.updateStatus(userId ,status);
    }

    @Override
    public void updateChatbotEnabled(int userId, Boolean status) throws RemoteException {
        userServer= UserServerImpl.getUserService();
        userServer.updateChatbotEnabled(userId,status);
    }

    @Override
    public boolean authenticateUser(String phoneNumber, String password) throws RemoteException {
        userServer= UserServerImpl.getUserService();
        return userServer.authenticateUser(phoneNumber ,password) ;
    }

    @Override
    public void updateOnline(int userId, Boolean isOnline) throws RemoteException {
        userServer= UserServerImpl.getUserService();
        userServer.updateOnline(userId, isOnline);
    }

    @Override
    public void updateUserInfo(User user) throws RemoteException {

        userServer= UserServerImpl.getUserService();
        userServer.updateUser(user);
    }

    @Override
    public void updateUserImage(int userID, byte[] img) throws RemoteException {
        userServer= UserServerImpl.getUserService();
        userServer.updateUserImage(userID, img);
    }
    //we need this unction as two functions one to get the chats for a user and another one to get messages in one chat
    @Override
    public HashMap<Chat, List<Message>> getAllChats(int id) throws RemoteException {
        return null;
    }

    @Override
    public List<Message> getChatMessages(int chatID) throws RemoteException {
        MessageService messageService=new MessageService();
       return messageService.getChatMessages(chatID);
        //Message message=new Message("bjkbjhvjh",LocalDateTime.now(),6,5);
        //return new ArrayList<Message>(List.of(message));
    }



    @Override
    public ArrayList<User> getAllFriends(int userId) throws RemoteException {
        userServer= UserServerImpl.getUserService();

        return userServer.getFriendsUser(userId);
    }




    @Override
    public List<Invitation> getAllInvitation(int userId) throws RemoteException {
        return InvitationServerImpl.getInvitationServerImp().getUserFriendRequests(userId);
    }

    @Override
    public User getUser(int userID) throws RemoteException {
        userServer= UserServerImpl.getUserService();
        return userServer.findUserById(userID);
    }

    @Override
    public User getUser(String phoneNumber) throws RemoteException {
        userServer= UserServerImpl.getUserService();
        return userServer.findUserByPhoneNumber(phoneNumber);
    }

    @Override
    public Boolean sendFriendRequest(List<Invitation> addRequests) throws RemoteException {
        return InvitationServerImpl.getInvitationServerImp().sendFriendRequest(addRequests);
    }

    @Override
    public Boolean updateFriendsRequestStatus(Invitation invitation) throws RemoteException {
        boolean isUpdated = InvitationServerImpl.getInvitationServerImp().updateFriendsRequestStatus(invitation);

        InvitationService invitationService = new InvitationService();
        if(invitation.getStatus()==InvStatus.REJECT)return invitationService.deleteInvitation(invitation.getSenderId(),invitation.getReceiverId());

            int senderId = invitation.getSenderId();
            int receiverId = invitation.getReceiverId();
            {
              Chat chat = new Chat("");
              ChatService chatService = new ChatService();
             int chatId =    chatService.addNewChat(chat);
                List<Participant> participants = new ArrayList<>();

                participants.add(new Participant(chatId, senderId,Participant.State.AVAILABLE,Participant.Category.FRIEND));
                participants.add(new Participant(chatId, receiverId,Participant.State.AVAILABLE,Participant.Category.FRIEND));

                ParticipantService participantService = new ParticipantService();

                participantService.addListOfParticipant(participants);


                if(clients.containsKey(receiverId)) {

                    userServer= UserServerImpl.getUserService();
                    User user=userServer.findUserById(receiverId);

                    ChatServerImpl chatSer =  new ChatServerImpl();

                    Message message = new Message("now we are friends ", LocalDateTime.now(),chatId, receiverId );

                    MessageService service = new MessageService();

                    service.addMessage(message);

                    ChatCard chatCard = new ChatCard();
                    chatCard.setChat_id(chatId);
                    chatCard.setChat_name(user.getName());
                    chatCard.setUser_Id(receiverId);
                    chatCard.setUser_name(user.getName());
                    chatCard.setUser_isOnline(user.getIsOnline());
                    chatCard.setUser_pictrue(user.getPicture() != null ? chatSer.toBoxedBytes(user.getPicture()) : null);










                    ClientRepository client = clients.get(receiverId);

                    client.moveaNewCardtoTop(chatCard);
                }
            }


        return isUpdated;
    }

    @Override
    public void sendMessage( Message message) throws RemoteException {
        ChatServerImpl chatServer=new ChatServerImpl();
        chatServer.addMessage(message);
        Notification notification = notificationServer.createNotification("sent a message", Timestamp.valueOf(LocalDateTime.now()),message.getUser_id(), true ,message.getChat_id());
        List<Participant>p=chatServer.getChatParticipants(message.getChat_id());
        if(p!=null)
        {   for (Participant p1 :p)
        {
            if (clients.containsKey(p1.getParticpantId())&&p1.getParticpantId()!=message.getUser_id())
            {

                ClientRepository clientRepository =clients.get(p1.getParticpantId());
                clientRepository.getNotification(notification);
                clientRepository.sendMessage(message);
               clientRepository.receivedMessage(message);
            }
        }}


    }

    @Override
    public void createGroup(String Name, List<Integer> id) throws RemoteException {


        Chat chat = new Chat(Name);
        ChatService chatService = new ChatService();
        int chatId =    chatService.addNewChat(chat);
        List<Participant> participants = new ArrayList<>();





        for(Integer i: id)
        {




            if(clients.containsKey(i))
            {

              /*create card
                 ClientRepository clientRepository =clients.get(i);
                 clientRepository.y
                 ();

               */

            }
        }

    }
    //need service in the chat server impl to create chat
    @Override
    public int createChat(Chat chat) throws RemoteException {
        return 0;
    }



    @Override
    public void updateNotification(int NotificationId, int userId) throws RemoteException {
        NotificationImpl.getNotificationImpl().UpdateNotificationState(NotificationId,userId);
    }



    @Override
    public void sendInvitation(int user_id, List<User> users) throws RemoteException {
        InvitationServerImpl invitationServer= InvitationServerImpl.getInvitationServerImp();
        invitationServer.sendFriendRequest(user_id,users);
    }

    @Override
    public List<Announcement> getAllAnnouncememts() throws RemoteException {
        return null;
    }

    @Override
    public List<Notification> getAllNotifications(int userId) throws RemoteException {
        NotificationImpl notificationImpl=NotificationImpl.getNotificationImpl();
        return notificationImpl.getAllNotification(userId);
    }
    @Override
    public List<Participant> getChatParticipants(int chat_id)throws RemoteException {
        return (new ChatServerImpl()).getChatParticipants(chat_id);
    }

    @Override
    public Chat getChatById(int chatId) throws RemoteException {
        return (new ChatServerImpl()).getChatById(chatId);
    }



    @Override
    public ArrayList<Participant> geParticpantByChat(int id) throws RemoteException{
        ParticipantRepository participantRepository = new ParticipantService();
        return  participantRepository.geParticpantByChat(id);
    }

    // CHATS

    @Override
    public Chat getChat(int chatID) throws RemoteException {
        ChatRepository chatRepository = new ChatService();
        return chatRepository.getChatById(chatID);
    }

    @Override
    public List<Integer> getAllChatsById(int user_id) throws RemoteException{
       ParticipantRepository participantRepository = new ParticipantService();
       return participantRepository.getAllChatsById(user_id);
    }


    @Override
    public List<ChatCard> getChatsForUser(int userId) throws RemoteException{
        ChatServerRepository serverRepository = new ChatServerImpl();
        return serverRepository.getChatsForUser(userId);
    }

    @Override
    public int addNewChat(Chat chat) throws RemoteException {
        ChatRepository chatRepository = new ChatService();
        return chatRepository.addNewChat(chat);
    }

    @Override
    public void createParticpant(Participant p) throws RemoteException {
        ParticipantRepository participantRepository = new ParticipantService();
        participantRepository.createParticpant(p);
    }

    @Override
    public List<ChatCard> getChatsForUser(int userId, Participant.Category category) throws RemoteException {
        ChatServerRepository serverRepository = new ChatServerImpl();
        return serverRepository.getChatsForUser(userId , category);
    }

    @Override
    public boolean addMessage(Message message) throws RemoteException {
        MessageRepository messageRepository = new MessageService();
        return messageRepository.addMessage(message);
    }

    @Override
    public String getBotResponse(String userMessage) throws RemoteException {
        ChatBotServer chat = ChatBotServer.getInstance();
        return chat.getBotResponse(userMessage);
    }
    @Override
    public int addNewChat(Chat chat) throws RemoteException {
        ChatRepository chatRepository = new ChatService();
        return chatRepository.addNewChat(chat);
    }

    @Override
    public void createParticpant(Participant p) throws RemoteException {
        ParticipantRepository participantRepository = new ParticipantService();
        participantRepository.createParticpant(p);

        ChatService chatService = new ChatService();

        UserServerImpl server = UserServerImpl.getUserService();



        int chatId = p.getChatId();
        Chat chat = chatService.getChatById(chatId);
        int userId = p.getParticpantId();
        User user =  server.findUserById(userId);


        if(clients.containsKey(userId)) {
            ChatServerImpl chatSer =  new ChatServerImpl();
            ChatCard chatCard = new ChatCard();
            chatCard.setChat_id(chatId);
            chatCard.setChat_name(chat.getName());
            chatCard.setUser_Id(userId);
            chatCard.setUser_name(user.getName());
            chatCard.setUser_isOnline(user.getIsOnline());
            chatCard.setUser_pictrue(user.getPicture() != null ? chatSer.toBoxedBytes(user.getPicture()) : null);

            ClientRepository client = clients.get(userId);

            client.moveaNewCardtoTop(chatCard);
        }






    }

  /*  @Override
    public List<ChatCard> getChatsForUser(int userId, Participant.Category category) throws RemoteException {
        ChatServerRepository serverRepository = new ChatServerImpl();
        return serverRepository.getChatsForUser(userId , category);
    } */

    @Override
    public boolean addMessage(Message message) throws RemoteException {
        MessageRepository messageRepository = new MessageService();
        return messageRepository.addMessage(message);
    }



}
