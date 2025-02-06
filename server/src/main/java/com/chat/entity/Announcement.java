package com.chat.entity;

public class Announcement {
    private int announcementId;
    private String description;
    private int adminId;

    public Announcement() {}

    public Announcement(int announcementId, String description, int adminId) {
        this.announcementId = announcementId;
        this.description = description;
        this.adminId = adminId;
    }

    public int getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(int announcementId) {
        this.announcementId = announcementId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }
}
