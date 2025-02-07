package com.chat.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

import java.net.URL;
import java.util.ResourceBundle;

public class CountryController implements Initializable {

    @FXML PieChart pieChart;

    ObservableList<PieChart.Data> pieChartData = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("City A", 13),
                        new PieChart.Data("City B", 25),
                        new PieChart.Data("City C", 10),
                        new PieChart.Data("City D", 22),
                        new PieChart.Data("City E", 30));

        pieChart.setData(pieChartData);
        pieChart.setTitle("Sales in Following Cities");
    }

    public PieChart getPieChart() {
        return pieChart;
    }

}
