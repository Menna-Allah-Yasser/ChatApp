package com.chat;
import com.chat.controller.BarController;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ClientStarter extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        final int initWidth = 1000;
        final int initHeight = 600;
        final Pane root = new Pane();
        Pane controller= null;
        stage.setOnCloseRequest((e)->{
            Cordinator.getScheduledExecutorService().shutdown();});

        File file =  new File("user_session.properties");
        if(!file.exists())
        {
            controller = loadFXML("signUp");
        }
        else
        {
            Properties properties = new Properties();

            try(FileInputStream in =  new FileInputStream("user_session.properties"))
            {
                properties.load(in);


                {System.out.println("File exists, loading Login...");
                    controller = loadFXML("login");

                }
                }
            }




        controller.setPrefWidth(initWidth);
        controller.setPrefHeight(initHeight);
        root.getChildren().add(controller);


        Scale scale = new Scale(1, 1, 0, 0);
        scale.xProperty().bind(root.widthProperty().divide(initWidth));
        scale.yProperty().bind(root.heightProperty().divide(initHeight));
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