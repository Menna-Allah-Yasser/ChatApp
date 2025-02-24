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

    private InvitationServerImpl() {
        invitationService = new InvitationService();
    }

    public static synchronized InvitationServerImpl getInvitationServerImp() {
      
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
    public boolean acceptFriendRequest(Invitation request) {
        Invitation inv = new Invitation(request.getSenderId(), request.getReceiverId(), InvStatus.ACCEPT);
        return invitationService.updateStatus(inv);
    }

    @Override
    public boolean rejectFriendRequest(Invitation request) {
        Invitation inv = new Invitation(request.getSenderId(), request.getReceiverId(), InvStatus.REJECT);
        return invitationService.updateStatus(inv);
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

    @Override
    public boolean sendFriendRequest(List<Invitation> invitations) {
        int senderId = invitations.get(0).getSenderId();
        List<Invitation> requestsToSend = new ArrayList<>();
        List<Invitation> receivedInvitations = invitationService.getAllInvitationsByStatus(senderId, InvStatus.WAIT);

        Map<Integer, Invitation> invitationMap = new HashMap<>();
        for (Invitation invitation : receivedInvitations) {
            invitationMap.put(invitation.getSenderId(), invitation);
        }

        for(Invitation inv : invitations) {
            Invitation existingInvitation = invitationMap.get(inv.getReceiverId());
            if(existingInvitation != null) {
                existingInvitation.setStatus(InvStatus.ACCEPT);
                invitationService.updateStatus(existingInvitation);
            } else {
                requestsToSend.add(inv);
            }
        }
        return invitationService.addListOfInvitation(requestsToSend);
    }

}
