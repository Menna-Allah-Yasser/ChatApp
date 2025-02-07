package com.chat.service.impl;

import com.chat.dao.impl.InvitationService;
import com.chat.entity.InvStatus;
import com.chat.entity.Invitation;
import com.chat.entity.User;
import com.chat.service.repository.InvitationServerRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvitationServerImpl implements InvitationServerRepository {

    private static InvitationServerImpl invitationServer=null;
    InvitationService invitationService;
    private InvitationServerImpl(){invitationService=new InvitationService();}
    public static InvitationServerImpl getInvitationServerImpl()
    {
        if(invitationServer==null)
        {
            invitationServer=new InvitationServerImpl();
        }
        return invitationServer;
    }
    @Override
    public List<Invitation> getUserFriendRequests(int userID) {
        return invitationService.getAllInvitationsByStatus(userID, InvStatus.WAIT);
    }

    @Override
    public List<Integer> getUserFriends(int userID) {
        return invitationService.getFriends(userID);
    }

    @Override
    public boolean updateFriendsRequestStatus(Invitation request) {
        return invitationService.updateStatus(request);
    }

    @Override
    public boolean sendFriendRequest(int senderId, List<User> users) {
        List<Invitation> requests = new ArrayList<>();
        List<Invitation> receivedInvitations = invitationService.getAllInvitationsByStatus(senderId, InvStatus.WAIT);

        Map<Integer, Invitation> invitationMap = new HashMap<>();
        for (Invitation invitation : receivedInvitations) {
            invitationMap.put(invitation.getSenderId(), invitation);
        }

        for (User user : users) {
            Invitation existingInvitation = invitationMap.get(user.getUserId());
            if (existingInvitation != null) {
                existingInvitation.setStatus(InvStatus.ACCEPT);
                invitationService.updateStatus(existingInvitation);
            } else {
                requests.add(new Invitation(senderId, user.getUserId(), InvStatus.WAIT));
            }
        }
        return invitationService.addListOfInvitation(requests);
    }

}
