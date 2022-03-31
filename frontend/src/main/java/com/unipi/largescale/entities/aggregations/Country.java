package com.unipi.largescale.entities.aggregations;

public class Country {

    String id;
    double avg;

    public Country() {
    }

    public Country(String id, double avg) {
        this.id = id;
        this.avg = avg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id='" + id + '\'' +
                ", avg=" + avg +
                '}';
    }
}
