package com.unipi.largescale.entities;

import com.unipi.largescale.beans.CommentBean;
import com.unipi.largescale.beans.SongBean;
import com.unipi.largescale.dtos.CommentSubsetDto;
import com.unipi.largescale.dtos.InterfaceSongDto;
import com.unipi.largescale.dtos.SongDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Song {
    private String id;
    private String name;
    private String album;
    private List<String> artists;
    private int trackNumber;
    private int discNumber;
    private boolean explicit;
    private int key;
    private int mode;
    private double danceability;
    private double energy;
    private double loudness;
    private double speechiness;
    private double acousticness;
    private double instrumentalness;
    private double liveness;
    private double valence;
    private int tempo;
    private int duration;
    private int timeSignature;
    private int year;
    private String image;
    private List<Comment> comments;
    private int cluster;
    private int numLikes;
    private int numUnlikes;

    public Song(String id, String name, String album, List<String> artists, String image){
        this.id = id;
        this.name = name;
        this.album = album;
        this.artists = artists;
        this.image = Objects.requireNonNullElse(image, "https://upload.wikimedia.org/wikipedia/commons/thumb/8/80/Circle-icons-music.svg/1024px-Circle-icons-music.svg.png");
    }

    public Song(String name, String album, List<String> artists, int year, String image, double danceability, double energy, double loudness, double speechiness, double acousticness, double instrumentalness, double liveness, double valence){
        this.name = name;
        this.artists = artists;
        this.album = album;
        this.year = year;
        this.image = image;
        this.danceability = danceability;
        this.energy = energy;
        this.loudness = loudness;
        this.speechiness = speechiness;
        this.acousticness = acousticness;
        this.instrumentalness = instrumentalness;
        this.liveness = liveness;
        this.valence = valence;
    }

    public Song(SongBean songBean){
        id = songBean.getId();
        name = songBean.getName();
        artists = Arrays.stream(songBean.getArtists().split(",")).toList();
        album = songBean.getAlbum();
        year = songBean.getYear();
        danceability = songBean.getDanceability();
        energy = songBean.getEnergy();
        loudness = songBean.getLoudness();
        speechiness = songBean.getSpeechiness();
        acousticness = songBean.getAcousticness();
        instrumentalness = songBean.getInstrumentalness();
        liveness = songBean.getLiveness();
        valence = songBean.getValence();
        List<Comment> list = new ArrayList<>();
        if(songBean.getComments() != null && songBean.getComments().size() != 0) {
            for (CommentBean comment : songBean.getComments())
                list.add(new Comment(comment));
        }
        comments = list;
        cluster = songBean.getCluster();
        numLikes = songBean.getNumLikes();
        numUnlikes = songBean.getNumUnlikes();
    }

    public Song(InterfaceSongDto song){
        this.id = song.getId();
        this.name = song.getName();
        this.image = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/80/Circle-icons-music.svg/1024px-Circle-icons-music.svg.png";
        this.artists = Arrays.stream(song.getArtists().split(",")).toList();
        this.album = song.getAlbum();
    }

    public Song(SongDto song) {
        this.id = song.getId();
        this.name = song.getName();
        this.image = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/80/Circle-icons-music.svg/1024px-Circle-icons-music.svg.png";
        this.artists = song.getArtists();
        this.album = song.getAlbum();
        this.acousticness = song.getAcousticness();
        this.danceability = song.getDanceability();
        this.duration = song.getDuration();
        this.discNumber = song.getDiscNumber();
        this.explicit = song.isExplicit();
        this.energy = song.getEnergy();
        this.instrumentalness = song.getInstrumentalness();
        this.liveness = song.getLiveness();
        this.trackNumber = song.getTrackNumber();
        this.key = song.getKey();
        this.mode = song.getMode();
        this.loudness = song.getLoudness();
        this.speechiness = song.getSpeechiness();
        this.valence = song.getValence();
        this.tempo = song.getTempo();
        this.duration = song.getDuration();
        this.timeSignature = song.getTimeSignature();
        this.year = song.getYear();
        List<Comment> list = new ArrayList<>();
        if (song.getComments() != null){
            for (CommentSubsetDto comment : song.getComments())
                list.add(new Comment(comment));
        }
        this.comments = list;
        this.cluster = song.getCluster();
        this.numLikes = 0;
        for(int i = 0; i < song.getLikes().size(); ++i) {
            this.numLikes += song.getLikes().get(i).getNumLikes();
            this.numUnlikes += song.getLikes().get(i).getNumUnlikes();
        }
    }

    public String getId() {
        return id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlbum() {
        return album;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public List<String> getArtists() {
        return artists;
    }

    public void setArtists(List<String> artists) {
        this.artists = artists;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean getExplicit() {
        return explicit;
    }

    public boolean isExplicit() {
        return explicit;
    }

    public double getDanceability() {
        return danceability;
    }

    public int getDiscNumber() {
        return discNumber;
    }

    public double getEnergy() {
        return energy;
    }

    public double getAcousticness() {
        return acousticness;
    }

    public double getInstrumentalness() {
        return instrumentalness;
    }

    public double getLoudness() {
        return loudness;
    }

    public int getKey() {
        return key;
    }

    public double getSpeechiness() {
        return speechiness;
    }

    public int getMode() {
        return mode;
    }

    public double getLiveness() {
        return liveness;
    }

    public void setDanceability(double danceability) {
        this.danceability = danceability;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public void setDiscNumber(int discNumber) {
        this.discNumber = discNumber;
    }

    public void setExplicit(boolean explicit) {
        this.explicit = explicit;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public void setAcousticness(double acousticness) {
        this.acousticness = acousticness;
    }

    public double getValence() {
        return valence;
    }

    public int getTempo() {
        return tempo;
    }

    public void setInstrumentalness(double instrumentalness) {
        this.instrumentalness = instrumentalness;
    }

    public void setTrackNumber(int trackNumber) {
        this.trackNumber = trackNumber;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public void setLoudness(double loudness) {
        this.loudness = loudness;
    }

    public int getDuration() {
        return duration;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public void setSpeechiness(double speechiness) {
        this.speechiness = speechiness;
    }

    public void setLiveness(double liveness) {
        this.liveness = liveness;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getTimeSignature() {
        return timeSignature;
    }

    public int getYear() {
        return year;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }

    public void setTimeSignature(int timeSignature) {
        this.timeSignature = timeSignature;
    }

    public void setValence(double valence) {
        this.valence = valence;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setCluster(int cluster) {
        this.cluster = cluster;
    }

    public int getCluster() {
        return cluster;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public int getNumUnlikes() {
        return numUnlikes;
    }

    public void setNumLikes(int numLikes) {
        this.numLikes = numLikes;
    }

    public void setNumUnlikes(int numUnlikes) {
        this.numUnlikes = numUnlikes;
    }
}


