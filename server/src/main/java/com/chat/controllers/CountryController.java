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
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class CountryController implements Initializable {

    @FXML
    private PieChart pieChart;

    private Map<String, Long> lastCountryCounts = null;

    private final String[] sliceColors = {"#8E24AA", "#7B1FA2", "#6A1B9A", "#4A148C"};

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateChart();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> updateChart()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateChart() {
        ObservableList<User> users = HomeController.getObservableUsers();

        Map<String, Long> countryCounts = users.stream()
                .filter(u -> u.getCountry() != null)
                .collect(Collectors.groupingBy(User::getCountry, Collectors.counting()));

        if (countryCounts.equals(lastCountryCounts)) {
            return;
        }
        lastCountryCounts = countryCounts;

        Platform.runLater(() -> {
            ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(
                    countryCounts.entrySet().stream()
                            .map(entry -> new PieChart.Data(entry.getKey(), entry.getValue()))
                            .collect(Collectors.toList())
            );
            pieChart.setData(pieData);
            pieChart.setTitle("Country Distribution");

            double total = countryCounts.values().stream().mapToDouble(Long::doubleValue).sum();
            for (PieChart.Data data : pieData) {
                double percentage = (data.getPieValue() / total) * 100;
                data.setName(data.getName() + " " + String.format("%.1f%%", percentage));
            }

            Timeline delay = new Timeline(new KeyFrame(Duration.millis(150), ae -> {
                int index = 0;
                for (PieChart.Data data : pieChart.getData()) {
                    Node sliceNode = data.getNode();
                    if (sliceNode != null) {
                        String color = sliceColors[index % sliceColors.length];
                        sliceNode.setStyle("-fx-pie-color: " + color + ";");
                    }
                    index++;
                }
            }));
            delay.play();
        });
    }
}
