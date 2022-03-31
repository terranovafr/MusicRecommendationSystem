package com.unipi.largescale.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

import static com.unipi.largescale.service.UserService.*;
import static com.unipi.largescale.util.UtilGUI.getPersonalityDescription;

public class FXMLStatsDocumentController implements Initializable{
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private BarChart personalityBarChart;
    @FXML
    private BarChart clusterBarChart;
    @FXML
    private Text deviation;
    @FXML
    private Text deviationNN;
    @FXML
    private Text personalityDescription;

    @FXML
    private void showMusicStats(ActionEvent event){
        System.out.println("Showing music stats");
        LoaderFXML object = new LoaderFXML();
        Pane musicStatsPane = object.getPage("musicStats");
        try {
            if(anchorPane != null)
                anchorPane.getChildren().clear();
            assert anchorPane != null;
            anchorPane.getChildren().add(musicStatsPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        personalityBarChart.setStyle("-fx-bar-fill: blue;");
        // personal bar chart
        XYChart.Series<String, Number> values = new XYChart.Series<>();
        double[] personalityValues = getPersonalityValues();
        double[] clusterPersonalityValues = getClusterPersonalityValues();
        values.getData().add(new XYChart.Data<>("OPN", personalityValues[0]));
        values.getData().add(new XYChart.Data<>("AGR", personalityValues[1]));
        values.getData().add(new XYChart.Data<>("NSM", personalityValues[2]));
        values.getData().add(new XYChart.Data<>("EXT", personalityValues[3]));
        values.getData().add(new XYChart.Data<>("CNS", personalityValues[4]));
        values.getData().add(new XYChart.Data<>("Time", personalityValues[5]));
        personalityBarChart.getData().addAll(values);
        // cluster bar chart
        XYChart.Series<String, Number> valuesCluster = new XYChart.Series<>();
        if(clusterPersonalityValues != null) {
            valuesCluster.getData().add(new XYChart.Data<>("OPN", clusterPersonalityValues[0]));
            valuesCluster.getData().add(new XYChart.Data<>("AGR", clusterPersonalityValues[1]));
            valuesCluster.getData().add(new XYChart.Data<>("NSM", clusterPersonalityValues[2]));
            valuesCluster.getData().add(new XYChart.Data<>("EXT", clusterPersonalityValues[3]));
            valuesCluster.getData().add(new XYChart.Data<>("CNS", clusterPersonalityValues[4]));
            valuesCluster.getData().add(new XYChart.Data<>("Time", clusterPersonalityValues[5]));
            clusterBarChart.getData().addAll(valuesCluster);
        }
        deviation.setText("Your behavior deviates "+ getDeviation() + "% with respect to others of your cluster.");
        String deviationNNValue = getDeviationNN();
        if(deviationNNValue != null)
            deviationNN.setText("Your behavior deviates "+ deviationNNValue + "% with respect to your nearest neighbor.");
        else deviationNN.setText("");
        personalityDescription.setText(getPersonalityDescription(user));
    }
}
