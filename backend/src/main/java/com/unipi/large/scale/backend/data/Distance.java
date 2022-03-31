package com.unipi.large.scale.backend.data;

public class Distance {

    private String userId;
    private double distance;

    public Distance(String userId, double distance) {
        this.userId = userId;
        this.distance = distance;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Distance{" +
                "userId='" + userId + '\'' +
                ", distance=" + distance +
                '}';
    }
}
