package com.unipi.largescale.entities.aggregations;

public class HighestVarianceCluster {

    int id;
    double difference;

    public HighestVarianceCluster() {
    }

    public HighestVarianceCluster(int id, double difference) {
        this.id = id;
        this.difference = difference;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getDifference() {
        return difference;
    }

    public void setDifference(double difference) {
        this.difference = difference;
    }

    @Override
    public String toString() {
        return "HighestVarianceCluster{" +
                "id=" + id +
                ", difference=" + difference +
                '}';
    }
}
