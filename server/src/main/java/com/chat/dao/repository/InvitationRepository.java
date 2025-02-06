package com.chat.dao.repository;

import com.chat.dto.Invitationdto;
import com.chat.entity.Invitation;

import java.util.List;

public interface InvitationRepository {

    boolean addInvitation(Invitation invitation);

    boolean addListOfInvitation(List<Invitation> invitations);

    List<Invitationdto> getInvitationsByReceiverId(int receiver_id);

    boolean updateStatus(int sender_id , int receiver_id);
}
