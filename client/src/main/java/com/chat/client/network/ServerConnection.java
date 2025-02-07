package com.chat.client.network;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ServerConnection {

    private static ServerRepository server;

    private final static String serverURI = "rmi://localhost:1901/ChatServer";

    public ServerConnection() {
    }

    public static ServerRepository getServer()
    {
          if(server == null)
            {
                try {
                    server =(ServerRepository) Naming.lookup(serverURI);
                }  catch (RemoteException | NotBoundException | MalformedURLException e)
                {}
            }
          return server;

    }

    public  static void disconnect()
    {
        server = null;
    }
}
