package com.unipi.large.scale.backend.entities.mongodb;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Document("song")
public class MongoSong {

    @MongoId
    private ObjectId id;
    private String name;
    private String album;
    private Set<String> artists = new HashSet<>();
    @Field("track_number")
    private int trackNumber;
    @Field("disc_number")
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
    @Field("time_signature")
    private int timeSignature;
    private int year;
    private List<CommentSubset> comments;
    private int cluster;
    private List<Like> likes;

    public MongoSong() {
    }

    public MongoSong(ObjectId id, String name, String album, Set<String> artists, int trackNumber, int discNumber, boolean explicit, int key, int mode, double danceability, double energy, double loudness, double speechiness, double acousticness, double instrumentalness, double liveness, double valence, int tempo, int duration, int timeSignature, int year, List<CommentSubset> comments, int cluster, List<Like> likes) {
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
        this.comments = comments;
        this.cluster = cluster;
        this.likes = likes;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getArtists() {
        return artists;
    }

    public void setArtists(Set<String> artists) {
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

    @Override
    public String toString() {
        return "MongoSong{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", artists=" + artists +
                ", trackNumber=" + trackNumber +
                ", discNumber=" + discNumber +
                ", explicit=" + explicit +
                ", key=" + key +
                ", mode=" + mode +
                ", danceability=" + danceability +
                ", energy=" + energy +
                ", loudness=" + loudness +
                ", speechiness=" + speechiness +
                ", acousticness=" + acousticness +
                ", instrumentalness=" + instrumentalness +
                ", liveness=" + liveness +
                ", valence=" + valence +
                ", tempo=" + tempo +
                ", duration=" + duration +
                ", timeSignature=" + timeSignature +
                ", year=" + year +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MongoSong mongoSong = (MongoSong) o;
        return id.equals(mongoSong.id) && name.equals(mongoSong.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public List<CommentSubset> getComments() {
        return comments;
    }

    public void setComments(List<CommentSubset> comments) {
        this.comments = comments;
    }

    public void addComment(CommentSubset comment){
        this.comments.add(comment);
    }

    public void removeComment(CommentSubset comment){
        this.comments.remove(comment);
    }

    public int getCluster() {
        return cluster;
    }

    public void setCluster(int cluster) {
        this.cluster = cluster;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }
}
