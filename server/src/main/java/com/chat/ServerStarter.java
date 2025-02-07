package com.chat;

import com.chat.entity.InvStatus;
import com.chat.entity.Invitation;
import com.chat.service.impl.InvitationServerImpl;

import java.util.List;

public class ServerStarter {
    public static void main(String[] args) {

        System.out.println(InvitationServerImpl.getInvitationServerImp().getUserFriends(1));



    }
}