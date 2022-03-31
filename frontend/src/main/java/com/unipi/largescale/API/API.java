package com.unipi.largescale.API;
import com.unipi.largescale.dtos.*;
import com.unipi.largescale.entities.*;
import com.unipi.largescale.entities.aggregations.Album;
import com.unipi.largescale.entities.aggregations.AverageMusicFeatures;
import com.unipi.largescale.entities.aggregations.Country;
import com.unipi.largescale.entities.aggregations.Id;
import com.unipi.largescale.util.ConfigurationParameters;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.*;


public class API {
    private static String uri;
    private static final RestTemplate restTemplate = new RestTemplate();

    public static void setConfiguration(ConfigurationParameters confParameters){
        if(confParameters.serverIP != null && confParameters.serverPort != 0)
            uri = "http://" + confParameters.serverIP + ":" + confParameters.serverPort;
        else uri = "http://localhost:8080"; //default parameters
    }

    public static User registerUser(User user) throws Exception{
        UserDto userDto = new UserDto(user);
        try {
            UserDto response = restTemplate.postForObject(uri + "/users/register", userDto, UserDto.class);
            assert response != null;
            user.setId(response.getId());
            user.setCluster(response.getCluster());
        } catch(Exception e){
            throw new Exception(e.getMessage().split("\"")[10]);
        }
        return user;
    }

    public static void createSong(Song song){
        SongDto songDto = new SongDto(song);
        try {
            SongDto response = restTemplate.postForObject(uri + "/songs/create", songDto, SongDto.class);
            assert response != null;
        } catch(Exception e){
            System.out.println(e.getMessage().split("\"")[10]);
        }
    }

    public static User loginUser(User user) throws Exception{
        LoginDto loginDto = new LoginDto(user);
        UserDto response;
        try {
            response = restTemplate.postForObject(uri + "/users/login", loginDto, UserDto.class);
        } catch(Exception e){
            if(e.getMessage().split("\"").length == 3)
                throw new Exception("Server not connected....");
            else throw new Exception(e.getMessage().split("\"")[10]);
        }
        if(response != null)
            return new User(response);
        else return null;
    }

    public static User getUserInfo(String id){
        ResponseEntity<UserDto> response;
        try {
            response = restTemplate.getForEntity(uri + "/users/" + id, UserDto.class);
        } catch(Exception e){
            System.out.println(e.getMessage().split("\"")[10]);
            return null;
        }
        if(response.getBody() != null ) {
            return new User(response.getBody());
        } else return null;
    }

    public static List<Comment> getSongComments(Song song){
        ResponseEntity<CommentDto[]> response;
        try {
            response = restTemplate.getForEntity(uri + "/songs/" + song.getId() + "/comment", CommentDto[].class);
        } catch(Exception e){
            System.out.println(e.getMessage().split("\"")[10]);
            return null;
        }
        if(response.getBody() != null ) {
            return Arrays.stream(response.getBody()).map(Comment::new).toList();
        } else return null;
    }

    public static List<User> getSimilarUsers(User user){
        ResponseEntity<InterfaceUserDto[]> response;
        try {
            response = restTemplate.getForEntity(uri + "/users/similarities/" + user.getId(), InterfaceUserDto[].class);
        } catch(Exception e){
            System.out.println(e.getMessage().split("\"")[10]);
            return null;
        }
        if(response.getBody() != null ) {
            return Arrays.stream(response.getBody()).map(User::new).toList();
        } else return null;
    }


    public static List<Song> getRecommendedSongs(User user){
        ResponseEntity<InterfaceSongDto[]> response;
        try {
            response = restTemplate.getForEntity(uri + "/songs/recommended/" + user.getId(), InterfaceSongDto[].class);
        } catch(Exception e){
            System.out.println(e.getMessage().split("\"")[10]);
            return null;
        }
        if(response.getBody() != null ) {
            return Arrays.stream(response.getBody()).map(Song::new).toList();
        } else return null;
    }

    public static List<User> getNearbyUsers(User user){
        ResponseEntity<InterfaceUserDto[]> response;
        try {
            response = restTemplate.getForEntity(uri + "/users/similar_nearby/" + user.getId(), InterfaceUserDto[].class);
        } catch(Exception e){
            System.out.println(e.getMessage().split("\"")[10]);
            return null;
        }
        if(response.getBody() != null )
            return Arrays.stream(response.getBody()).map(User::new).toList();
        else return null;
    }

    public static List<User> searchUserByUsername(String username){
        ResponseEntity<InterfaceUserDto[]> response;
        try {
            response = restTemplate.getForEntity(uri + "/users/search/" + username, InterfaceUserDto[].class);
        } catch(Exception e){
            System.out.println(e.getMessage().split("\"")[10]);
            return null;
        }
        if(response.getBody() != null )
            return Arrays.stream(response.getBody()).map(User::new).toList();
        else return null;
    }

    public static List<Song> searchSongByName(String name){
        ResponseEntity<InterfaceSongDto[]> response;
        try {
            response = restTemplate.getForEntity(uri + "/songs/search/" + name, InterfaceSongDto[].class);
        } catch(Exception e){
            System.out.println(e.getMessage().split("\"")[10]);
            return null;
        }

        if(response.getBody() != null ) {
            for(InterfaceSongDto in: response.getBody())
                in.setArtists(in.getArtists().substring(1, in.getArtists().length() - 1));
            return Arrays.stream(response.getBody()).map(Song::new).toList();
        } else return null;
    }

    public static Survey getClusterValues(int id){
        ResponseEntity<SurveyDto> response;
        try {
            response = restTemplate.getForEntity(uri + "/users/cluster_cluster_values/" + id, SurveyDto.class);
        } catch(Exception e){
            System.out.println(e.getMessage().split("\"")[10]);
            return null;
        }
        if(response.getBody() != null )
            return new Survey(response.getBody());
        else return null;
    }

    public static User getMostSimilarUser(User user){
        ResponseEntity<UserDto> response;
        try {
            response = restTemplate.getForEntity(uri + "/users/most_similar/" + user.getId(), UserDto.class);
        } catch(Exception e){
            System.out.println(e.getMessage().split("\"")[10]);
            return null;
        }
        if(response.getBody() != null )
            return new User(response.getBody());
        else return null;
    }

    public static Song getSong(Song song){
        ResponseEntity<SongDto> response;
        try {
            response = restTemplate.getForEntity(uri + "/songs/" + song.getId(), SongDto.class);
        } catch(Exception e){
            System.out.println(e.getMessage().split("\"")[10]);
            return null;
        }
        if(response.getBody() != null )
            return new Song(response.getBody());
        else return null;
    }

    public static List<User> getFriendRequests(User user){
        ResponseEntity<InterfaceUserDto[]> response = null;
        try {
            response = restTemplate.getForEntity(uri + "/users/friend_requests/" + user.getId(), InterfaceUserDto[].class);
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        assert response != null;
        if(response.getBody() != null)
            return Arrays.stream(response.getBody()).map(User::new).toList();
        else return null;
    }

    public static List<User> getFriendships(User user){
        ResponseEntity<InterfaceUserDto[]> response = null;
        try {
            response = restTemplate.getForEntity(uri + "/users/friends/" + user.getId(), InterfaceUserDto[].class);
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        assert response != null;
        if(response.getBody() != null )
            return Arrays.stream(response.getBody()).map(User::new).toList();
        else return null;
    }

    public static void updateFriendRequest(User user, User receiver, int status){
        try {
           restTemplate.exchange(uri + "/users/friend_requests/?from=" + user.getId() + "&to=" + receiver.getId() + "&status=" + status, HttpMethod.PUT, null, Void.class);
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }


    public static void quarantineUser(User user){
        try {
            restTemplate.exchange(uri + "/users/" + user.getId() + "/quarantine", HttpMethod.PUT, null, Void.class);
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void deleteSong(Song song){
        try {
            restTemplate.exchange(uri + "/songs/" + song.getId(), HttpMethod.DELETE, null, Void.class);
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void deleteUser(User user){
        try {
            restTemplate.exchange(uri + "/users/" + user.getId(), HttpMethod.DELETE, null, Void.class);
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }


    public static void updateUserInfo(User user){
        HttpEntity<UserDto> entity = new HttpEntity<>(new UserDto(user));
        restTemplate.exchange(uri + "/users/update/" ,
                    HttpMethod.PUT,
                    entity, Void.class);
    }

     public static void addFriendRequest(User sender, String receiverId){
         try {
             restTemplate.postForEntity(uri + "/users/friend_requests/?from=" + sender.getId() + "&to=" + receiverId, null, Object.class);
         } catch(Exception e){
             System.out.println(e.getMessage());
         }
     }

    public static Comment commentSong(Song song, Comment comment){
        CommentDto commentDto = new CommentDto(comment);
        ResponseEntity<CommentDto> response = null;
        try {
            response = restTemplate.postForEntity(uri + "/songs/" + song.getId() + "/comment", commentDto, CommentDto.class);
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        assert response != null;
        if(response.getBody() != null )
            return new Comment(response.getBody());
        else return null;
    }

    public static void updatePreferenceSong(User user, Song song, int status){
        try {
            restTemplate.exchange(uri + "/songs/like/?from=" + user.getId() + "&to=" + song.getId() + "&status=" + status, HttpMethod.PUT, null, Void.class);
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static int getClusterHighestVariance(){
        ResponseEntity<Integer> response;
        try {
            response = restTemplate.getForEntity(uri + "/users/clusters/highest_variance", Integer.class);
        } catch(Exception e){
            System.out.println(e.getMessage());
            return -1;
        }
        if(response.getBody() != null ) {
            return response.getBody();
        } else return -1;
    }

    public static Id getMostDanceableCluster(){
        ResponseEntity<Id> response;
        try {
            response = restTemplate.getForEntity(uri + "/songs/most_danceable_cluster", Id.class);
        } catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        if(response.getBody() != null ) {
            return response.getBody();
        } else return null;
    }

    public static List<Album> getClusterKHighestRatedAlbums(int cluster, int k){
        ResponseEntity<Album[]> response;
        try {
            response = restTemplate.getForEntity(uri + "/songs/top_albums/?cluster=" + cluster + "&k=" + k, Album[].class);
        } catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        if(response.getBody() != null ) {
            return Arrays.stream(response.getBody()).toList();
        } else return null;
    }

    public static List<Country> getTopKCountries(int k){
        ResponseEntity<Country[]> response;
        try {
            response = restTemplate.getForEntity(uri + "/users/top_countries/?k=" + k, Country[].class);
        } catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        if(response.getBody() != null ) {
            return Arrays.stream(response.getBody()).toList();
        } else return null;
    }

    public static List<AverageMusicFeatures> getAverageMusicFeaturesByCluster(){
        ResponseEntity<AverageMusicFeatures[]> response;
        try {
            response = restTemplate.getForEntity(uri + "/songs/average_music_features", AverageMusicFeatures[].class);
        } catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        if(response.getBody() != null ) {
            return Arrays.stream(response.getBody()).toList();
        } else return null;
    }

     public static void checkForUpdates(User user){
         try {
             restTemplate.postForEntity(uri + "/users/similarities/" + user.getId(), null, Object.class);
         } catch(Exception e){
             System.out.println(e.getMessage());
         }
     }
}
