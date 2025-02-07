package com.chat.utils;

import com.chat.ClientStarter;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Director {
    static Class<?> mainClass = ClientStarter.class;




    // Method to load FXML into a Node (for general usage)
    public static Node loadView(String fxmlUrl) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(mainClass.getResource(fxmlUrl+".fxml"));
            return fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException("Unable to load FXML: " + fxmlUrl);
        }
    }


       public  static void loadView(ActionEvent event, String fxmlUrl) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(mainClass.getResource(fxmlUrl));
                Scene scene = new Scene(fxmlLoader.load());
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
            } catch (IOException e) {
                throw new RuntimeException("Unable to load");
            }


    }
}
