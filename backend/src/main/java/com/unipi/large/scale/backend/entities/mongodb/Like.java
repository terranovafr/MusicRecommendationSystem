package com.unipi.large.scale.backend.entities.mongodb;

public class Like {

    private int cluster;
    private int numLikes;
    private int numUnlikes;

    public Like(int cluster, int numLikes, int numUnlikes) {
        this.cluster = cluster;
        this.numLikes = numLikes;
        this.numUnlikes = numUnlikes;
    }

    public Like() {
    }

    public int getCluster() {
        return cluster;
    }

    public void setCluster(int cluster) {
        this.cluster = cluster;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(int numLikes) {
        this.numLikes = numLikes;
    }

    public int getNumUnlikes() {
        return numUnlikes;
    }

    public void setNumUnlikes(int numUnlikes) {
        this.numUnlikes = numUnlikes;
    }

    @Override
    public String toString() {
        return "Likes{" +
                "cluster=" + cluster +
                ", numLikes=" + numLikes +
                ", numUnlikes=" + numUnlikes +
                '}';
    }
}
