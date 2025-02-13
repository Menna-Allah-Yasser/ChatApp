package com.chat.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;

public class Announcement implements Serializable {
    private int announcementId;
    private String description;
    private int adminId;
    private Timestamp time;

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

    public Announcement(String description, int adminId, Timestamp time) {
        this.description = description;
        this.adminId = adminId;
        this.time = time;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
