package com.chat.controllers;


import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class LandingController implements Initializable {

    @FXML
    private StackPane landingCenter;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label sloganLabel;

    @FXML
    private Button getStartedButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FadeTransition fade = new FadeTransition(Duration.seconds(2), landingCenter);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();

        TranslateTransition slide = new TranslateTransition(Duration.seconds(1), welcomeLabel);
        slide.setFromY(-50);
        slide.setToY(0);
        slide.play();

        getStartedButton.setOnAction(e -> {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.5), getStartedButton);
            scaleTransition.setFromX(1.0);
            scaleTransition.setFromY(1.0);
            scaleTransition.setToX(1.1);
            scaleTransition.setToY(1.1);
            scaleTransition.setCycleCount(2);
            scaleTransition.setAutoReverse(true);
            scaleTransition.play();

            FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), landingCenter);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.play();
        });
    }

}
