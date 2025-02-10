package com.chat.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;

public class ChatCard implements Serializable {

    private int chat_id;

    private String chat_name;

    private int message_id;

    private String message_desc;

    private LocalDateTime message_time;

    private int user_Id;

    private String user_name;

    private boolean user_isOnline;

    private byte[] user_pictrue;


    public ChatCard() {
    }

    public ChatCard(int chat_id , String chat_name , int message_id, String message_desc, LocalDateTime message_time, int user_Id, String user_name, boolean user_isOnline, byte[] user_pictrue) {

        this.chat_id=chat_id;
        this.chat_name=chat_name;
        this.message_id = message_id;
        this.message_desc = message_desc;
        this.message_time = message_time;
        this.user_Id = user_Id;
        this.user_name = user_name;
        this.user_isOnline = user_isOnline;
        this.user_pictrue = user_pictrue;
    }

    public int getChat_id() {
        return chat_id;
    }

    public String getChat_name() {
        return chat_name;
    }

    public int getMessage_id() {
        return message_id;
    }

    public String getMessage_desc() {
        return message_desc;
    }

    public LocalDateTime getMessage_time() {
        return message_time;
    }

    public int getUser_Id() {
        return user_Id;
    }

    public String getUser_name() {
        return user_name;
    }

    public boolean isUser_isOnline() {
        return user_isOnline;
    }

    public byte[] getUser_pictrue() {
        return user_pictrue;
    }

    public void setChat_id(int chat_id) {
        this.chat_id = chat_id;
    }

    public void setChat_name(String chat_name) {
        this.chat_name = chat_name;
    }

    public void setMessage_id(int message_id) {
        this.message_id = message_id;
    }

    public void setMessage_desc(String message_desc) {
        this.message_desc = message_desc;
    }

    public void setMessage_time(LocalDateTime message_time) {
        this.message_time = message_time;
    }

    public void setUser_Id(int user_Id) {
        this.user_Id = user_Id;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setUser_isOnline(boolean user_isOnline) {
        this.user_isOnline = user_isOnline;
    }

    public void setUser_pictrue(byte[] user_pictrue) {
        this.user_pictrue = user_pictrue;
    }


    @Override
    public String toString() {
        return "ChatCard{" +
                "chat_id=" + chat_id +
                ", chat_name='" + chat_name + '\'' +
                ", message_id=" + message_id +
                ", message_desc='" + message_desc + '\'' +
                ", message_time=" + message_time +
                ", user_Id=" + user_Id +
                ", user_name='" + user_name + '\'' +
                ", user_isOnline=" + user_isOnline +
                ", user_pictrue=" + Arrays.toString(user_pictrue) +
                '}';
    }
}
