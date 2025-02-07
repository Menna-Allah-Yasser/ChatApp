package com.chat.controller;

public class FriendRequest {
    private  String name="khaled shaban";
    private  String message= "sent you a friend request";
    private String profileImage="avatar1.png";

    public FriendRequest(String name, String message, String profileImage) {
        this.name = name;
        this.message = message;
        this.profileImage = profileImage;
    }
    public FriendRequest(){}

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public String getProfileImage() {
        return profileImage;
    }
}
