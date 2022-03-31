package com.unipi.largescale.gui;

import com.unipi.largescale.beans.SongBean;
import com.unipi.largescale.entities.Song;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

import static com.unipi.largescale.service.SongService.*;

public class FXMLSongInfoDocumentController implements Initializable{
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField name;
    @FXML
    private TextField album;
    @FXML
    private TextField artist;
    @FXML
    private TextField yearOfRelease;
    @FXML
    private BarChart featureBarChart;
    @FXML
    private Label mainCluster;
    @FXML
    private Label numLikes;
    @FXML
    private Label numUnlikes;
    @FXML
    private ImageView image;
    public static SongBean selectedSong;

    @FXML
    private void showComments(ActionEvent event){
        featureBarChart.setStyle("-fx-bar-fill: blue;");
        System.out.println("Loading the stats page");
        LoaderFXML object = new LoaderFXML();
        Pane commentsPane = object.getPage("comments");
        try {
            anchorPane.getChildren().clear();
            anchorPane.getChildren().add(commentsPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        featureBarChart.setStyle("-fx-bar-fill: blue;");
        selectedSong = new SongBean(getMoreInformationSong(new Song(selectedSong)));
        XYChart.Series<String, Number> values = new XYChart.Series<>();
        name.setText(selectedSong.getName());
        album.setText(selectedSong.getAlbum());
        artist.setText(selectedSong.getArtists());
        image.setImage(selectedSong.getImage().getImage());
        yearOfRelease.setText(String.valueOf(selectedSong.getYear()));
        name.setEditable(false);
        album.setEditable(false);
        artist.setEditable(false);
        yearOfRelease.setEditable(false);
        values.getData().add(new XYChart.Data<>("Danceability", selectedSong.getDanceability()));
        values.getData().add(new XYChart.Data<>("Acousticness", selectedSong.getAcousticness()));
        values.getData().add(new XYChart.Data<>("Energy", selectedSong.getEnergy()));
        values.getData().add(new XYChart.Data<>("Instrumentalness", selectedSong.getInstrumentalness()));
        values.getData().add(new XYChart.Data<>("Liveness", selectedSong.getLiveness()));
        values.getData().add(new XYChart.Data<>("Valence", selectedSong.getValence()));
        numLikes.setText(String.valueOf(selectedSong.getNumLikes()));
        numUnlikes.setText(String.valueOf(selectedSong.getNumUnlikes()));
        featureBarChart.getData().addAll(values);
        mainCluster.setText(String.valueOf(selectedSong.getCluster()));
    }
}
