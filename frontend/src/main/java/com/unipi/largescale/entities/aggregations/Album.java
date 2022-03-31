package com.unipi.largescale.entities.aggregations;

public class Album {

    String id;
    int strength;

    public Album() {
    }

    public Album(String id, int strength) {
        this.id = id;
        this.strength = strength;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    @Override
    public String toString() {
        return "Album{" +
                "id='" + id + '\'' +
                ", strength=" + strength +
                '}';
    }
}
