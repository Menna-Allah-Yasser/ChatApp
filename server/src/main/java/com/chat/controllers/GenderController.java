package com.chat.controllers;

import com.chat.entity.User;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class GenderController implements Initializable {

    @FXML
    private PieChart pieChart;

    private int lastMaleCount = -1;
    private int lastFemaleCount = -1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateChart();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> updateChart()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateChart() {
        ObservableList<User> users = HomeController.getObservableUsers();

        int maleCount = (int) users.stream()
                .filter(u -> u.getGender() != null && u.getGender().equalsIgnoreCase("male"))
                .count();
        int femaleCount = (int) users.stream()
                .filter(u -> u.getGender() != null && u.getGender().equalsIgnoreCase("female"))
                .count();

        if (maleCount == lastMaleCount && femaleCount == lastFemaleCount) {
            return;
        }
        lastMaleCount = maleCount;
        lastFemaleCount = femaleCount;

        Platform.runLater(() -> {
            ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(
                    new PieChart.Data("Male", maleCount),
                    new PieChart.Data("Female", femaleCount)
            );
            pieChart.setData(pieData);
            pieChart.setTitle("Gender Distribution");

            double total = maleCount + femaleCount;
            for (PieChart.Data data : pieData) {
                double percentage = (data.getPieValue() / total) * 100;
                data.setName(data.getName() + " " + String.format("%.1f%%", percentage));
            }

            if (!pieData.isEmpty() && pieData.get(0).getNode() != null) {
                pieData.get(0).getNode().setStyle("-fx-pie-color: #8A2BE2;");
            }
            if (pieData.size() > 1 && pieData.get(1).getNode() != null) {
                pieData.get(1).getNode().setStyle("-fx-pie-color: #6A5ACD;");
            }

            Set<Node> legendSymbols = pieChart.lookupAll(".chart-legend-item-symbol");
            int index = 0;
            for (Node symbol : legendSymbols) {
                if (index == 0) {
                    symbol.setStyle("-fx-background-color: #8A2BE2;");
                } else if (index == 1) {
                    symbol.setStyle("-fx-background-color: #6A5ACD;");
                }
                index++;
            }
        });
    }
}
