package com.chat.service.repository;


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatBotRepository{
    String getBotResponse(String userMessage);
}
