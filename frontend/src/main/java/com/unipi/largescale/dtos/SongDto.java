package com.unipi.largescale.dtos;

import com.unipi.largescale.entities.Song;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SongDto implements Serializable {

    private String id;
    private String name;
    private String album;
    private List<String> artists = new ArrayList<>();
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
    private List<CommentSubsetDto> comments;
    private int cluster;
    private List<LikeDto> likes;

    public SongDto() {
    }

    public SongDto(String id, String name, String album, List<String> artists, int trackNumber, int discNumber, boolean explicit, int key, int mode, double danceability, double energy, double loudness, double speechiness, double acousticness, double instrumentalness, double liveness, double valence, int tempo, int duration, int timeSignature, int year) {
        this.id = id;
        this.name = name;
        this.album = album;
        this.artists = artists;
        this.trackNumber = trackNumber;
        this.discNumber = discNumber;
        this.explicit = explicit;
        this.key = key;
        this.mode = mode;
        this.danceability = danceability;
        this.energy = energy;
        this.loudness = loudness;
        this.speechiness = speechiness;
        this.acousticness = acousticness;
        this.instrumentalness = instrumentalness;
        this.liveness = liveness;
        this.valence = valence;
        this.tempo = tempo;
        this.duration = duration;
        this.timeSignature = timeSignature;
        this.year = year;
    }

    public SongDto(Song song){
        this.id = song.getId();
        this.name = song.getName();
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
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public List<String> getArtists() {
        return artists;
    }

    public void setArtists(List<String> artists) {
        this.artists = artists;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(int trackNumber) {
        this.trackNumber = trackNumber;
    }

    public int getDiscNumber() {
        return discNumber;
    }

    public void setDiscNumber(int discNumber) {
        this.discNumber = discNumber;
    }

    public boolean isExplicit() {
        return explicit;
    }

    public void setExplicit(boolean explicit) {
        this.explicit = explicit;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public double getDanceability() {
        return danceability;
    }

    public void setDanceability(double danceability) {
        this.danceability = danceability;
    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public double getLoudness() {
        return loudness;
    }

    public void setLoudness(double loudness) {
        this.loudness = loudness;
    }

    public double getSpeechiness() {
        return speechiness;
    }

    public void setSpeechiness(double speechiness) {
        this.speechiness = speechiness;
    }

    public double getAcousticness() {
        return acousticness;
    }

    public void setAcousticness(double acousticness) {
        this.acousticness = acousticness;
    }

    public double getInstrumentalness() {
        return instrumentalness;
    }

    public void setInstrumentalness(double instrumentalness) {
        this.instrumentalness = instrumentalness;
    }

    public double getLiveness() {
        return liveness;
    }

    public void setLiveness(double liveness) {
        this.liveness = liveness;
    }

    public double getValence() {
        return valence;
    }

    public void setValence(double valence) {
        this.valence = valence;
    }

    public int getTempo() {
        return tempo;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getTimeSignature() {
        return timeSignature;
    }

    public void setTimeSignature(int timeSignature) {
        this.timeSignature = timeSignature;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<CommentSubsetDto> getComments() {
        return comments;
    }

    public void setComments(List<CommentSubsetDto> comments) {
        this.comments = comments;
    }

    public int getCluster() {
        return cluster;
    }

    public void setCluster(int cluster) {
        this.cluster = cluster;
    }

    public void setLikes(List<LikeDto> likes) {
        this.likes = likes;
    }

    public List<LikeDto> getLikes() {
        return likes;
    }
}
