package com.unipi.largescale.gui;

import com.unipi.largescale.entities.aggregations.Album;
import com.unipi.largescale.entities.aggregations.AverageMusicFeatures;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static com.unipi.largescale.service.SongService.*;
import static com.unipi.largescale.service.UserService.*;



public class FXMLMusicStatsDocumentController implements Initializable{
    @FXML
    private BarChart musicBarChart;
    @FXML
    private Label topAlbums;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        musicBarChart.setStyle("-fx-bar-fill: blue;");
        XYChart.Series<String, Number> values = new XYChart.Series<>();
        List<Album> albums = getClusterKHighestRatedAlbums();
        StringBuilder text = new StringBuilder();
        for(int i = 0; i < albums.size();++i) {
            text.append(albums.get(0).getId()).append("\n");
        }
        topAlbums.setText(text.toString());
        AverageMusicFeatures musicValues = getAverageClusterMusicValues();
        values.getData().add(new XYChart.Data<>("Danceability", musicValues.getDanceability()));
        values.getData().add(new XYChart.Data<>("Acousticness", musicValues.getAcousticness()));
        values.getData().add(new XYChart.Data<>("Energy", musicValues.getEnergy()));
        values.getData().add(new XYChart.Data<>("Valence", musicValues.getValence()));
        values.getData().add(new XYChart.Data<>("Instrumentalness", musicValues.getInstrumentalness()));
        values.getData().add(new XYChart.Data<>("Liveness", musicValues.getLiveness()));
        musicBarChart.getData().addAll(values);
    }
}
