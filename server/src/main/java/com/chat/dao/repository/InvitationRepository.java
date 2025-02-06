package com.chat.dao.repository;

import com.chat.dto.Invitationdto;
import com.chat.entity.InvStatus;
import com.chat.entity.Invitation;


import java.util.List;

public interface InvitationRepository {

    boolean addInvitation(Invitation invitation);

    boolean deleteInvitation(int senderId, int receiverId);

    public List<Invitation> getAllInvitationsByStatus(int receiver_id, InvStatus status);

    boolean addListOfInvitation(List<Invitation> invitations);

    List<Invitation> getAllInvitationsByReceiverId(int receiver_id);

    boolean updateStatus(Invitation newInv);
}
