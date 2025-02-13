package com.chat.entity;

import javafx.beans.property.*;

import java.time.LocalDateTime;

public class ChatCardClient {
    private final IntegerProperty chatId = new SimpleIntegerProperty();
    private final StringProperty chatName = new SimpleStringProperty();
    private final IntegerProperty messageId = new SimpleIntegerProperty();
    private final StringProperty messageDesc = new SimpleStringProperty();
    private final ObjectProperty<LocalDateTime> messageTime = new SimpleObjectProperty<>();
    private final IntegerProperty userId = new SimpleIntegerProperty();
    private final StringProperty userName = new SimpleStringProperty();
    private final BooleanProperty userIsOnline = new SimpleBooleanProperty();
    private final ObjectProperty<byte[]> userPicture = new SimpleObjectProperty<>();

    public ChatCardClient() {}

    public ChatCardClient(int chatId, String chatName, int messageId, String messageDesc,
                    LocalDateTime messageTime, int userId, String userName,
                    boolean userIsOnline, byte[] userPicture) {

        this.chatId.set(chatId);
        this.chatName.set(chatName);
        this.messageId.set(messageId);
        this.messageDesc.set(messageDesc);
        this.messageTime.set(messageTime);
        this.userId.set(userId);
        this.userName.set(userName);
        this.userIsOnline.set(userIsOnline);
        this.userPicture.set(userPicture);
    }

    public ChatCardClient(int chatId, String chatName, int userId, String userName, boolean userIsOnline, byte[] userPictrue) {

        this.chatId.set(chatId);
        this.chatName.set(chatName);

        this.userId.set(userId);
        this.userName.set(userName);
        this.userIsOnline.set(userIsOnline);
        this.userPicture.set(userPictrue);

    }

    public IntegerProperty chatIdProperty() { return chatId; }
    public StringProperty chatNameProperty() { return chatName; }
    public IntegerProperty messageIdProperty() { return messageId; }
    public StringProperty messageDescProperty() { return messageDesc; }
    public ObjectProperty<LocalDateTime> messageTimeProperty() { return messageTime; }
    public IntegerProperty userIdProperty() { return userId; }
    public StringProperty userNameProperty() { return userName; }
    public BooleanProperty userIsOnlineProperty() { return userIsOnline; }
    public ObjectProperty<byte[]> userPictureProperty() { return userPicture; }

    public int getChatId() {
        return chatId.get();
    }

    public void setChatId(int chatId) {
        this.chatId.set(chatId);
    }

    public String getChatName() {
        return chatName.get();
    }

    public void setChatName(String chatName) {
        this.chatName.set(chatName);
    }

    public int getMessageId() {
        return messageId.get();
    }

    public void setMessageId(int messageId) {
        this.messageId.set(messageId);
    }

    public String getMessageDesc() {
        return messageDesc.get();
    }

    public void setMessageDesc(String messageDesc) {
        this.messageDesc.set(messageDesc);
    }

    public byte[] getUserPicture() {
        return userPicture.get();
    }

    public void setUserPicture(byte[] userPicture) {
        this.userPicture.set(userPicture);
    }

    public LocalDateTime getMessageTime() {
        return messageTime.get();
    }

    public void setMessageTime(LocalDateTime messageTime) {
        this.messageTime.set(messageTime);
    }

    public int getUserId() {
        return userId.get();
    }

    public void setUserId(int userId) {
        this.userId.set(userId);
    }

    public String getUserName() {
        return userName.get();
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    @Override
    public String toString() {
        return "ChatCardClient{" +
                "chatId=" + chatId +
                ", chatName=" + chatName +
                ", messageId=" + messageId +
                ", messageDesc=" + messageDesc +
                ", messageTime=" + messageTime +
                ", userId=" + userId +
                ", userName=" + userName +
                ", userIsOnline=" + userIsOnline +
                ", userPicture=" + userPicture +
                '}';
    }

    public boolean isUserIsOnline() {
        return userIsOnline.get();
    }

    public void setUserIsOnline(boolean userIsOnline) {
        this.userIsOnline.set(userIsOnline);
    }
}
