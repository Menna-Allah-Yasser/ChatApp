package com.chat.client.controller;

public class Notification {
    private  String sender="khaled shaban";
    private  String message="sent you a message";
    private  String avatarPath="avatar1.png";
    private  boolean isUnread=true;

    public Notification(String sender, String message, String avatarPath, boolean isUnread) {
        this.sender = sender;
        this.message = message;
        this.avatarPath = avatarPath;
        this.isUnread = isUnread;
    }
    public Notification(){}
    public Notification( boolean isUnread){this.isUnread = isUnread;}

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public boolean isUnread() {
        return isUnread;
    }
}
