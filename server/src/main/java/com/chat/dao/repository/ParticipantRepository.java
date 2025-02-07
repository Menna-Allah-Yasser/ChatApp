package com.chat.dao.repository;

import com.chat.entity.Participant;

import java.util.ArrayList;
import java.util.List;

public interface ParticipantRepository {
    
    public Participant geParticpant(int id, int chat_id);

    public ArrayList<Participant> geParticpantByChat(int id);

    public void createParticpant(Participant p);

    public void updateParticpant(Participant p);

    public void deleteParticpant(Participant p);

    public ArrayList<Participant> geParticpants(int id);

    List<Integer> getChatsIdUsingUserIdAndCategory(int user_id, Participant.Category category);

    boolean addListOfParticipant(List<Participant> participants);

    List<Integer> getAllChatsById(int user_id);


}
