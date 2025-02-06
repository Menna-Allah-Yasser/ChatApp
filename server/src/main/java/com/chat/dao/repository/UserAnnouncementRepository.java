package com.chat.dao.repository;

import com.chat.entity.UserAnnouncement;

import java.util.List;

public interface UserAnnouncementRepository {

    boolean addUserAnnouncement(UserAnnouncement userAnnouncement);

    List<UserAnnouncement> getAnnouncementsByUserId(int userId);

    boolean deleteUserAnnouncement(int userId, int announcementId);

    boolean addListOfUserAnnouncements(List<UserAnnouncement> userAnnouncements);
}
