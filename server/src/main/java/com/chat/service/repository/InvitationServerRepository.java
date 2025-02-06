package com.chat.service.repository;

import com.chat.entity.Invitation;
import com.chat.entity.User;

import java.util.List;

public interface InvitationServerRepository {
    public List<Integer> getUserFriends(int userID);
    public List<Invitation> getUserFriendRequests(int userID);
    public boolean updateFriendsRequestStatus(Invitation request);
    public boolean sendFriendRequest(int senderId, List<User> users);
}
