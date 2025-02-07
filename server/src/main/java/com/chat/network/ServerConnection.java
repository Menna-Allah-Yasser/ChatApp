package com.chat.network;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class ServerConnection {

    private static ServerConnection serverConnection;
    private final String serverURI = "rmi://localhost:1901/ChatServer";

    private ServerConnection(){
        try {
            LocateRegistry.createRegistry(1901);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public static ServerConnection getServerConnectionInstance(){
        if(serverConnection ==null)
            serverConnection = new ServerConnection();
        return serverConnection;
    }

    public void startConnection(){
        try {
            Naming.rebind(serverURI,ServerImpl.getServer());
        }catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void stopConnection(){
        try {
            Naming.unbind(serverURI);
        } catch (MalformedURLException | RemoteException | NotBoundException e) {
            System.out.println("Server Already Disconnected");
        }
    }

    public static void main(String[] args) {
        ServerConnection connection = new ServerConnection();
        connection.startConnection();
    }
}
