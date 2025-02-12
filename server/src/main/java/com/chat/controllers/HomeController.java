package com.chat.controllers;

import com.chat.ServerStarter;
import com.chat.dao.impl.UserService;
import com.chat.entity.User;
import com.chat.enums.ServerState;
import com.chat.network.ServerConnection;
import com.chat.util.ServerStateManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HomeController implements Initializable {

    @FXML
    private Button btnOne;

    @FXML
    private Button btnTwo;

    @FXML
    private Button btnThree;

    @FXML
    private BorderPane mainPane;

    @FXML
    private Button signOutButton;

    public static ObservableList<User> observableUsers;


    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    UserService userService = new UserService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Node node = Director.loadView("landing.fxml");
        mainPane.setCenter(node);
        scheduler.scheduleAtFixedRate(() -> {
            List<User> users = userService.getAllUsers();
            observableUsers = FXCollections.observableArrayList(users);
        }, 0, 10, TimeUnit.SECONDS);
    }

    public void btnOneHandler(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ServerStarter.class.getResource("Announcments.fxml"));
        Node view = Director.loadView("Announcments.fxml");
        mainPane.setCenter(view);
    }

    public void btnTwoHandler(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ServerStarter.class.getResource("statsBar.fxml"));
        Node view =fxmlLoader.load();
        mainPane.setCenter(view);
    }

    public void btnThreeHandler(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ServerStarter.class.getResource("server.fxml"));
        Node view =fxmlLoader.load();
        mainPane.setCenter(view);
    }

    public void btnFourHandler(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ServerStarter.class.getResource("register.fxml"));
        Node view =fxmlLoader.load();
        mainPane.setCenter(view);
    }


    public void signOutHandler(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?", ButtonType.YES, ButtonType.NO);
        alert.setHeaderText(null);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            ServerStateManager.getServerConnection().stopConnection();
            ServerStateManager.setServerState(ServerState.STOPPED);
            FXMLLoader fxmlLoader = new FXMLLoader(ServerStarter.class.getResource("signin.fxml"));
            Parent view =fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(view, 1000, 600));
            stage.show();
        }
    }


    public static ObservableList<User> getObservableUsers() {
        return observableUsers;
    }

}
