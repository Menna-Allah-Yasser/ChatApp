package com.chat.controllers;

import com.chat.dao.impl.UserService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.util.Duration;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

public class CountryController implements Initializable {

    @FXML
    private PieChart pieChart;

    private final UserService userService = new UserService();
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
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                List<String> countries = userService.getCountries();
                Map<String, Long> countryCounts = countries.stream()
                        .collect(Collectors.groupingBy(c -> c, Collectors.counting()));

                if (countryCounts.equals(lastCountryCounts)) {return null;}
                lastCountryCounts = countryCounts;

                Platform.runLater(() -> {
                    List<PieChart.Data> dataList = countryCounts.entrySet().stream()
                            .map(entry -> new PieChart.Data(entry.getKey(), entry.getValue()))
                            .collect(Collectors.toList());
                    pieChart.setData(javafx.collections.FXCollections.observableArrayList(dataList));

                    double total = countryCounts.values().stream().mapToDouble(Long::doubleValue).sum();
                    for (PieChart.Data data : pieChart.getData()) {
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
                return null;
            }
        };

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
}
