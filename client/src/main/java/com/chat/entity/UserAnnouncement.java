package com.chat.entity;

import java.io.Serializable;

public class UserAnnouncement implements Serializable {

    private int userId;
    private int announcementId;

    // Constructors
    public UserAnnouncement(int userId, int announcementId) {
        this.userId = userId;
        this.announcementId = announcementId;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(int announcementId) {
        this.announcementId = announcementId;
    }
}
