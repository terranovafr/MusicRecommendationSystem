package com.unipi.largescale.service;

import com.unipi.largescale.API.API;
import com.unipi.largescale.entities.Comment;
import com.unipi.largescale.entities.Song;
import com.unipi.largescale.entities.aggregations.AverageMusicFeatures;

import java.util.ArrayList;
import java.util.List;

import static com.unipi.largescale.API.API.updatePreferenceSong;
import static com.unipi.largescale.service.UserService.user;

public class SongService {

    public static void likeSong(Song song){
        updatePreferenceSong(user, song, +1);
    }

    public static void unlikeSong(Song song){
        updatePreferenceSong(user, song, -1);
    }

    public static List<Comment> showAllComments(Song song){
        return API.getSongComments(song);
    }

    public static List<Comment> showComments(Song song){
        return song.getComments();
    }

    public static Comment commentSong(String text, Song song){
        return API.commentSong(song, new Comment(user.getId(), song.getId(), user.getFirstName(),user.getLastName(), text));
    }

    public static AverageMusicFeatures getAverageClusterMusicValues(){
        List<AverageMusicFeatures> list = API.getAverageMusicFeaturesByCluster();
        assert list != null;
        return list.get(user.getCluster());
    }

    public static Song getMoreInformationSong(Song selectedSong){
        return API.getSong(selectedSong);
    }

    public static List<Song> getRecommendedSongs(){
        return API.getRecommendedSongs(user);
    }

    public static List<Song> getSongsByName(String name){
        return API.searchSongByName(name);
    }

    public static void deleteSong(Song song){
        API.deleteSong(song);
    }

    public static void addNewSong(String name, String album, String artist, int year, String image, double danceability, double energy, double loudness, double speechiness, double acousticness, double instrumentalness, double liveness, double valence) {
        List<String> list = new ArrayList<>();
        list.add(artist);
        API.createSong(new Song(name, album, list, year, image, danceability, energy, loudness, speechiness, acousticness, instrumentalness, liveness, valence));
    }

}
