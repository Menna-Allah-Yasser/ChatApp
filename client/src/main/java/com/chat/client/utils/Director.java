package com.chat.client.utils;

import com.chat.client.ClientStarter;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Director {
    static Class<?> mainClass = ClientStarter.class;

    // Method to load FXML into a Node (for general usage)
    public static Node loadView(String fxmlUrl) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(mainClass.getResource(fxmlUrl + ".fxml"));
            return fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException("Unable to load FXML: " + fxmlUrl);
        }
    }

    // Method to load FXML and update the current scene (used with ActionEvent)
   public static void loadView(ActionEvent event, String fxmlUrl) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(mainClass.getResource(fxmlUrl + ".fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException("Unable to load FXML: " + fxmlUrl);
        }
    }
}
