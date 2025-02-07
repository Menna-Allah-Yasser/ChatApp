package com.chat.controllers;

import com.chat.ServerStarter;
import com.chat.enums.ServerState;
import com.chat.network.ClientRepository;
import com.chat.network.ServerConnection;
import com.chat.network.ServerImpl;
import com.chat.util.ServerStateManager;
import com.mysql.cj.xdevapi.Client;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

public class ServerStatusController implements Initializable {

    private ServerConnection serverConnection;

    @FXML
    private Button onButton;
    @FXML
    private Button offButton;

    private static final BooleanProperty isOn = new SimpleBooleanProperty();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        serverConnection = ServerStateManager.getServerConnection();
        isOn.bind(ServerStateManager.isOnProperty());
        updateButtonStyles();
        isOn.addListener((obs, oldValue, newValue) -> updateButtonStyles());
    }


    @FXML
    public void startServerButtonHandler(ActionEvent event) {
        serverConnection.startConnection();
        ServerStateManager.setServerState(ServerState.RUNNING);
    }

    @FXML
    void stopServerButtonHandler(ActionEvent event) {
        if (serverConnection != null) {
            serverConnection.stopConnection();
            ServerStateManager.setServerState(ServerState.STOPPED);
        } else {
            System.out.println("server is null");
        }
//        ConcurrentHashMap<Integer, ClientRepository> clients = ServerImpl.getOnlineClients();
//        for (ClientRepository client : clients.values()) {
//            try {
//                client.disconnect();
//            } catch (RemoteException e) {
//                e.printStackTrace();
//            }
//        }
    }

    private void updateButtonStyles() {
        if (isOn.get()) {
            onButton.getStyleClass().add("active");
            offButton.getStyleClass().remove("active");
        } else {
            offButton.getStyleClass().add("active");
            onButton.getStyleClass().remove("active");
        }
    }
}


