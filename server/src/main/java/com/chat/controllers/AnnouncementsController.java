package com.chat.controllers;

import com.chat.dao.impl.AnnouncementService;
import com.chat.entity.Announcement;
import com.mysql.cj.protocol.a.TimeTrackingPacketReader;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class AnnouncementsController {

    @FXML
    private TextArea announcementTextArea;

    @FXML
    private Button publish;

    @FXML
    public void initialize() {
        publish.setOnAction(event -> handlePublish());
    }

    private void handlePublish() {
        String announcement = announcementTextArea.getText();

        AnnouncementService announcementService = new AnnouncementService();
        announcementService.addAnnouncement(new Announcement(announcement, 19, Timestamp.valueOf(LocalDateTime.now())));
        System.out.println("Publishing announcement: " + announcement);

        announcementTextArea.clear();
        showAnnouncementPopup("Announcement published successfully!");
    }

    private void showAnnouncementPopup(String message) {
        Popup popup = new Popup();
        Label label = new Label(message);
        label.setStyle("-fx-background-color: rgba(128,0,128,0.7);" +
                "-fx-text-fill: white; " +
                "-fx-padding: 10px 20px; " +
                "-fx-font-size: 16px; " +
                "-fx-background-radius: 5;");
        popup.getContent().add(label);

        Stage stage = (Stage) publish.getScene().getWindow();
        popup.show(stage);

        double centerX = stage.getX() + stage.getWidth() / 2 + 100;
        double centerY = stage.getY() + stage.getHeight() / 2;

        label.applyCss();
        label.layout();
        popup.setX(centerX - label.getWidth() / 2);
        popup.setY(centerY - label.getHeight() / 2);

        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(e -> popup.hide());
        delay.play();
    }
}
