package com.unipi.largescale.entities.aggregations;

public class AverageMusicFeatures {

    int id;
    double danceability;
    double acousticness;
    double energy;
    double instrumentalness;
    double liveness;
    double valence;

    public AverageMusicFeatures() {
    }

    public AverageMusicFeatures(int id, double danceability, double acousticness, double energy, double instrumentalness, double liveness, double valence) {
        this.id = id;
        this.danceability = danceability;
        this.acousticness = acousticness;
        this.energy = energy;
        this.instrumentalness = instrumentalness;
        this.liveness = liveness;
        this.valence = valence;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getDanceability() {
        return danceability;
    }

    public void setDanceability(double danceability) {
        this.danceability = danceability;
    }

    public double getAcousticness() {
        return acousticness;
    }

    public void setAcousticness(double acousticness) {
        this.acousticness = acousticness;
    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
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

    @Override
    public String toString() {
        return "AverageMusicFeatures{" +
                "id=" + id +
                ", danceability=" + danceability +
                ", acousticness=" + acousticness +
                ", energy=" + energy +
                ", instrumentalness=" + instrumentalness +
                ", liveness=" + liveness +
                ", valence=" + valence +
                '}';
    }
}
