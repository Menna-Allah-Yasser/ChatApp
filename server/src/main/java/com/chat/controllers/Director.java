package com.chat.controllers;

import com.chat.ServerStarter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

public class Director {
    static Class<?> mainClass = ServerStarter.class;

    static Node loadView(String fxmlUrl) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(mainClass.getResource(fxmlUrl));
            return fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException("Unable to load FXML: " + fxmlUrl);
        }
    }
}
