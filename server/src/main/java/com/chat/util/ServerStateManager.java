package com.chat.util;

import com.chat.enums.ServerState;
import com.chat.network.ServerConnection;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;



public class ServerStateManager {
    private static ServerState serverState = ServerState.STOPPED;
    private static final BooleanProperty isOn = new SimpleBooleanProperty(false);
    private static ServerConnection serverConnection;

    public static ServerState getServerState() {
        return serverState;
    }

    public static void setServerState(ServerState state) {
        serverState = state;
        isOn.set(state == ServerState.RUNNING);
    }

    public static BooleanProperty isOnProperty() {
        return isOn;
    }

    public static ServerConnection getServerConnection() {
        if (serverConnection == null) {
            serverConnection = ServerConnection.getServerConnectionInstance();
        }
        return serverConnection;
    }
}

