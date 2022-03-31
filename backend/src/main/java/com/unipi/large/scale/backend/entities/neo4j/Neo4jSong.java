package com.unipi.large.scale.backend.entities.neo4j;

import java.util.List;
import java.util.Objects;

public class Neo4jSong {

    private String mongoId;
    private String name;
    private String artists;
    private String album;

    public Neo4jSong() {
    }


    public Neo4jSong(String mongoId, String name, String artists, String album) {
        this.mongoId = mongoId;
        this.name = name;
        this.artists = artists;
        this.album = album;
    }

    public Neo4jSong(String mongoId, String name, List<String> artists, String album) {
        this.mongoId = mongoId;
        this.name = name;
        this.artists = String.join(", ", artists);
        this.album = album;
    }

    public String getMongoId() {
        return mongoId;
    }

    public void setMongoId(String mongoId) {
        this.mongoId = mongoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtists() {
        return artists;
    }

    public void setArtists(String artists) {
        this.artists = artists;
    }

    public void setAuthors(List<String> authors) {
        this.artists = String.join(", ", authors);
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    @Override
    public String toString() {
        return "Neo4jSong{" +
                "mongoId=" + mongoId +
                ", name='" + name + '\'' +
                ", authors=" + artists +
                ", album='" + album + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Neo4jSong neo4jSong = (Neo4jSong) o;
        return mongoId.equals(neo4jSong.mongoId) && name.equals(neo4jSong.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mongoId, name);
    }
}
