package com.chat.dao.repository;

import com.chat.entity.Participant;

import java.util.List;

public interface ParticipantRepository {

    List<Integer> getChatsIdUsingUserIdAndCategory(int user_id , String category);

    boolean addListOfParticipant(List<Participant> participants);
}
