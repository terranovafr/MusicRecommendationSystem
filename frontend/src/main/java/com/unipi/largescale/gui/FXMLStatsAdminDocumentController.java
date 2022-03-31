package com.unipi.largescale.gui;

import com.unipi.largescale.entities.aggregations.Country;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static com.unipi.largescale.service.AdminService.*;
import static com.unipi.largescale.service.UserService.*;

public class FXMLStatsAdminDocumentController implements Initializable{
    @FXML
    private Label result;
    @FXML
    private BarChart barChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;

    @FXML
    private void showClusterHighestVariance(ActionEvent event){
        System.out.println("Showing the cluster with the highest variance to the admin");
        int cluster = getClusterHighestVariance();
        result.setText("The cluster with the highest variance is " + cluster);
        updateClusterBarChart(cluster);
    }

    @FXML
    private void showMostDanceableCluster(ActionEvent event){
        System.out.println("Showing the most danceable cluster to the admin");
        int cluster = getMostDanceableCluster();
        result.setText("The most danceable cluster is " + cluster);
        updateClusterBarChart(cluster);
    }

    @FXML
    private void showTopKStrongestCountries(ActionEvent event){
        System.out.println("Showing the top k strongest countries to the admin");
        List<Country> list = getTopKCountries();
        StringBuilder text = new StringBuilder("The top " + list.size() + " of the strongest countries are the following:\n");
        for(int i = 0; i < list.size(); ++i) {
           text.append(i).append(1).append(")").append(list.get(i).getId()).append("\n");
        }
        result.setText(text.toString());
        updateCountryBarChart(list);
    }

    private void updateCountryBarChart(List<Country> list){
        barChart.getData().clear();
        barChart.setStyle("-fx-bar-fill: blue;");
        XYChart.Series<String, Number> values = new XYChart.Series<>();
        for (Country country : list) values.getData().add(new XYChart.Data<>(country.getId(), country.getAvg()));
        barChart.getData().addAll(values);
        barChart.getData().clear();
        barChart.setStyle("-fx-bar-fill: blue;");
        values = new XYChart.Series<>();
        for (Country country : list) values.getData().add(new XYChart.Data<>(country.getId(), country.getAvg()));
        barChart.getData().addAll(values);
    }

    private void updateClusterBarChart(int cluster){
        barChart.getData().clear();
        barChart.setStyle("-fx-bar-fill: blue;");
        XYChart.Series<String, Number> values = new XYChart.Series<>();
        double[] personalityValues = getClusterPersonalityValues(cluster);
        assert personalityValues != null;
        values.getData().add(new XYChart.Data<>("OPN", personalityValues[0]));
        values.getData().add(new XYChart.Data<>("AGR", personalityValues[1]));
        values.getData().add(new XYChart.Data<>("NSM", personalityValues[2]));
        values.getData().add(new XYChart.Data<>("EXT", personalityValues[3]));
        values.getData().add(new XYChart.Data<>("CNS", personalityValues[4]));
        values.getData().add(new XYChart.Data<>("Time", personalityValues[5]));
        barChart.getData().addAll(values);
        barChart.getData().clear();
        barChart.setStyle("-fx-bar-fill: blue;");
        values = new XYChart.Series<>();
        personalityValues = getClusterPersonalityValues(cluster);
        assert personalityValues != null;
        values.getData().add(new XYChart.Data<>("OPN", personalityValues[0]));
        values.getData().add(new XYChart.Data<>("AGR", personalityValues[1]));
        values.getData().add(new XYChart.Data<>("NSM", personalityValues[2]));
        values.getData().add(new XYChart.Data<>("EXT", personalityValues[3]));
        values.getData().add(new XYChart.Data<>("CNS", personalityValues[4]));
        values.getData().add(new XYChart.Data<>("Time", personalityValues[5]));
        barChart.getData().addAll(values);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        xAxis.setAnimated(false);
        yAxis.setAnimated(false);
    }
}
