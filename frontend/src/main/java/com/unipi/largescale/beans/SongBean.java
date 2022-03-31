package com.unipi.largescale.beans;

import com.unipi.largescale.entities.Comment;
import com.unipi.largescale.entities.Song;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class SongBean {
    private SimpleStringProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty album;
    private SimpleStringProperty artists;
    private int year;
    private double danceability;
    private double energy;
    private double loudness;
    private double speechiness;
    private double acousticness;
    private double instrumentalness;
    private double liveness;
    private double valence;
    private ImageView image;
    private List<CommentBean> comments;
    private int cluster;
    private int numLikes;
    private int numUnlikes;


    public SongBean(Song song){
        id = new SimpleStringProperty(song.getId());
        name = new SimpleStringProperty(song.getName());
        album = new SimpleStringProperty(song.getAlbum());
        artists = new SimpleStringProperty(String.join(",", song.getArtists()));
        image = new ImageView(song.getImage());
        image.setFitHeight(30);
        image.setFitWidth(30);
        year = song.getYear();
        danceability = song.getDanceability();
        energy = song.getEnergy();
        loudness = song.getLoudness();
        speechiness = song.getSpeechiness();
        acousticness = song.getAcousticness();
        instrumentalness = song.getInstrumentalness();
        liveness = song.getLiveness();
        valence = song.getValence();
        List<CommentBean> list = new ArrayList<>();
        if(song.getComments() != null && song.getComments().size() != 0) {
            for (Comment comment : song.getComments())
                list.add(new CommentBean(comment));
        }
        comments = list;
        cluster = song.getCluster();
        numLikes = song.getNumLikes();
        numUnlikes = song.getNumUnlikes();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public void setAlbum(String album) {
        this.album.set(album);
    }

    public void setArtists(String artists) {
        this.artists.set(artists);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public ImageView getImage() {
        return image;
    }

    public String getAlbum() {
        return album.get();
    }

    public String getArtists() {
        return artists.get();
    }

    public String getName() {
        return name.get();
    }

    public String getId() {
        return id.get();
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setValence(double valence) {
        this.valence = valence;
    }

    public void setLiveness(double liveness) {
        this.liveness = liveness;
    }

    public int getYear() {
        return year;
    }

    public void setSpeechiness(double speechiness) {
        this.speechiness = speechiness;
    }

    public void setLoudness(double loudness) {
        this.loudness = loudness;
    }

    public void setInstrumentalness(double instrumentalness) {
        this.instrumentalness = instrumentalness;
    }

    public double getValence() {
        return valence;
    }

    public void setAcousticness(double acousticness) {
        this.acousticness = acousticness;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public void setDanceability(double danceability) {
        this.danceability = danceability;
    }

    public double getLiveness() {
        return liveness;
    }

    public double getLoudness() {
        return loudness;
    }

    public double getSpeechiness() {
        return speechiness;
    }

    public double getInstrumentalness() {
        return instrumentalness;
    }

    public double getAcousticness() {
        return acousticness;
    }

    public double getEnergy() {
        return energy;
    }

    public double getDanceability() {
        return danceability;
    }

    public void setComments(List<CommentBean> comments) {
        this.comments = comments;
    }

    public List<CommentBean> getComments() {
        return comments;
    }

    public void setNumUnlikes(int numUnlikes) {
        this.numUnlikes = numUnlikes;
    }

    public void setNumLikes(int numLikes) {
        this.numLikes = numLikes;
    }

    public int getNumUnlikes() {
        return numUnlikes;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public int getCluster() {
        return cluster;
    }

    public void setCluster(int cluster) {
        this.cluster = cluster;
    }
}


