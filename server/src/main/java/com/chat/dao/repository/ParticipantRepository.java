package com.chat.dao.repository;

import com.chat.entity.Participant;
import java.util.List;
import java.util.ArrayList;

public interface ParticipantRepository {
    public Participant geParticpant(int id);

    public ArrayList<Participant> geParticpantByChat(int id) ;

    public void createParticpant(Participant p) ;

    public void updateParticpant(Participant p) ;

    public void deleteParticpant(Participant p) ;

    public ArrayList<Participant> geParticpants(int id) ;

    List<Integer> getChatsIdUsingUserIdAndCategory(int user_id, String category);

    boolean addListOfParticipant(List<Participant> participants);

    List<Integer> getAllChatsById(int user_id);
}
