package com.unipi.largescale.dtos;

import java.io.Serializable;

public class LikeDto implements Serializable {

    private int cluster;
    private int numLikes;
    private int numUnlikes;

    public LikeDto() {
    }

    public LikeDto(int cluster, int numLikes, int numUnlikes) {
        this.cluster = cluster;
        this.numLikes = numLikes;
        this.numUnlikes = numUnlikes;
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
}
