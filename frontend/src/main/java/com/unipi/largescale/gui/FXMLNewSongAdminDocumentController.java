package com.unipi.largescale.gui;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.unipi.largescale.PersonalityClustering;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.ResourceBundle;

import static com.unipi.largescale.service.SongService.*;

import static com.unipi.largescale.gui.ValidationForm.validYear;


public class FXMLNewSongAdminDocumentController implements Initializable{
    @FXML
    private TextField name;
    @FXML
    private TextField album;
    @FXML
    private TextField artist;
    @FXML
    private TextField year;
    @FXML
    private Slider danceability;
    @FXML
    private Slider energy;
    @FXML
    private Slider loudness;
    @FXML
    private Slider speechiness;
    @FXML
    private Slider acousticness;
    @FXML
    private Slider instrumentalness;
    @FXML
    private Slider liveness;
    @FXML
    private Slider valence;
    @FXML
    private ImageView image;

    @FXML
    private void addSong(ActionEvent event){
        System.out.println("Adding a new song");
        String songName = name.getText();
        String songAlbum = album.getText();
        String songArtist = artist.getText();
        int songYear;
        String songYearString = year.getText();
        double songDanceability = danceability.getValue();
        double songEnergy = energy.getValue();
        double songLoudness = loudness.getValue();
        double songSpeechiness = speechiness.getValue();
        double songAcousticness = acousticness.getValue();
        double songInstrumentalness = instrumentalness.getValue();
        double songLiveness = liveness.getValue();
        double songValence = valence.getValue();
        String songImage = null;
        if(image.getImage() != null)
            songImage = image.getImage().getUrl();
        name.setStyle("-fx-border-color: null;");
        album.setStyle("-fx-border-color: null;");
        artist.setStyle("-fx-border-color: null;");
        year.setStyle("-fx-border-color: null;");
        if(songName.equals("")) {
            name.setStyle("-fx-border-color: #FF0000;");
            return;
        }
        if(songArtist.equals("")) {
            artist.setStyle("-fx-border-color: #FF0000;");
            return;
        }
        if(songAlbum.equals("")) {
            album.setStyle("-fx-border-color: #FF0000;");
            return;
        }
        if(songYearString.equals("")) {
            year.setStyle("-fx-border-color: #FF0000;");
            return;
        }
        try {
            songYear = Integer.parseInt(songYearString);
            if(!validYear(songYear)){
                throw new Exception();
            }
        } catch(Exception e){
            year.setStyle("-fx-border-color: #FF0000;");
            return;
        }
        addNewSong(songName, songAlbum, songArtist, songYear, songImage, songDanceability, songEnergy, songLoudness, songSpeechiness, songAcousticness, songInstrumentalness, songLiveness, songValence);
        name.setText("");
        album.setText("");
        artist.setText("");
        year.setText("");
        danceability.setValue(0.5);
        energy.setValue(0.5);
        loudness.setValue(0.5);
        speechiness.setValue(0.5);
        acousticness.setValue(0.5);
        instrumentalness.setValue(0.5);
        liveness.setValue(0.5);
        valence.setValue(0.5);
        image.setImage(new Image("https://upload.wikimedia.org/wikipedia/commons/thumb/8/80/Circle-icons-music.svg/1024px-Circle-icons-music.svg.png"));
    }

    @FXML
    private void uploadPic(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(PersonalityClustering.stage);
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dslbv6rz5",
                "api_key", "628347138896939",
                "api_secret", "k9zyTI4iM2nbYvQWLDDAn-18KBs",
                "secure", true));
        Map uploadedImage = cloudinary.uploader().upload(selectedFile, Collections.emptyMap());
        String imageUrl = uploadedImage.get("secure_url").toString();
        image.setImage(new Image(imageUrl));
        System.out.println("User uploaded a new profile picture");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        image.setImage(new Image("https://upload.wikimedia.org/wikipedia/commons/thumb/8/80/Circle-icons-music.svg/1024px-Circle-icons-music.svg.png"));
    }
}
