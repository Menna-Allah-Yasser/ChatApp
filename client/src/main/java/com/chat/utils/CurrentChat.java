package com.chat.utils;

import com.chat.entity.Chat;
import com.chat.entity.Participant;
import com.chat.entity.User;

import java.util.ArrayList;
import java.util.List;

public class CurrentChat {

    public static int chatId;


    public static Chat chat=null;
    public static User user=null;
    public static List<Participant> participants=new ArrayList<>();

}