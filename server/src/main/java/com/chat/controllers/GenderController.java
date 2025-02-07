package com.chat.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.ResourceBundle;

public class GenderController implements Initializable {

    @FXML BarChart<String, Number> barChart;

    ObservableList<XYChart.Series<String, Number>> barChartData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Sales Data");

        series.getData().add(new XYChart.Data<>("City A", 13));
        series.getData().add(new XYChart.Data<>("City B", 25));
        series.getData().add(new XYChart.Data<>("City C", 10));
        series.getData().add(new XYChart.Data<>("City D", 22));
        series.getData().add(new XYChart.Data<>("City E", 30));

        barChart.getData().add(series);
        barChart.setTitle("Sales in Following Cities");
    }

    public BarChart<String, Number> getBarChart() {
        return barChart;
    }
}
