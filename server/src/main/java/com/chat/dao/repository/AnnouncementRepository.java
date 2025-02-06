package com.chat.dao.repository;

import com.chat.entity.Announcement;

import java.util.List;

public interface AnnouncementRepository {

    boolean addAnnouncement(Announcement announcement);

    Announcement getAnnouncementById(int id);

    List<Announcement> getAllAnnouncements();

    boolean updateAnnouncement(Announcement announcement);

    boolean deleteAnnouncement(int id);
}
