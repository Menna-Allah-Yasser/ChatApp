package com.chat;

import com.chat.controller.BarController;
import com.chat.network.ServerConnection;
import com.chat.utils.Cordinator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.io.IOException;

public class ClientStarter extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        final int initWidth = 1000; // initial width
        final int initHeight = 600; // initial height
        final Pane root = new Pane(); // root container

        stage.setOnCloseRequest((e)->{
            Cordinator.getScheduledExecutorService().shutdown();
        });
        Pane controller = loadFXML("login");
        controller.setPrefWidth(initWidth);
        controller.setPrefHeight(initHeight);
        root.getChildren().add(controller);


        Scale scale = new Scale(1, 1, 0, 0);
        scale.xProperty().bind(root.widthProperty().divide(initWidth)); // binding scale X to width
        scale.yProperty().bind(root.heightProperty().divide(initHeight)); // binding scale Y to height
        root.getTransforms().add(scale);


        scene = new Scene(root, initWidth, initHeight);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();

        // Add listener for switching scenes
        scene.rootProperty().addListener(new ChangeListener<Parent>() {
            @Override
            public void changed(ObservableValue<? extends Parent> observable, Parent oldValue, Parent newValue) {
                scene.rootProperty().removeListener(this);

                if (newValue != null) {
                    scene.setRoot(root);
                    ((Region) newValue).setPrefWidth(initWidth);
                    ((Region) newValue).setPrefHeight(initHeight);

                    root.getChildren().clear();
                    root.getChildren().add(newValue);
                }

                scene.rootProperty().addListener(this);
            }
        });
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Pane loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientStarter.class.getResource(fxml + ".fxml"));
        Pane p= fxmlLoader.load();
        if(fxml=="Bar")
        {
            Cordinator.barController=fxmlLoader.getController();
        }
        return p;
    }

    public static void main(String[] args) {
        launch();
    }
}