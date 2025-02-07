package com.chat.network;

import com.chat.ServerStarter;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class ServerConnection {

    private static ServerConnection serverConnection;
    private final String serverURI = "rmi://localhost:1900/ChatUPServer";

    private ServerConnection(){
        try {
            LocateRegistry.createRegistry(1900);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public static ServerConnection getServerConnectionInstance(){
        if(serverConnection == null)
            serverConnection = new ServerConnection();
        return serverConnection;
    }

    public void startConnection(){
        try {
            Naming.rebind(serverURI,ServerImpl.getServer());
            System.out.println("server connected successfully");
        }catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void stopConnection(){
        try {
            Naming.unbind(serverURI);
            System.out.println("server disconnected successfuly");
        } catch (MalformedURLException | RemoteException | NotBoundException e) {
            System.out.println("Server Already Disconnected");
        }
    }

    public static void main(String[] args) {
        ServerConnection connection = new ServerConnection();
        connection.startConnection();
    }
}
