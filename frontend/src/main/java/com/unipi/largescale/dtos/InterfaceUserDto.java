package com.unipi.largescale.dtos;

import java.io.Serializable;

public class InterfaceUserDto implements Serializable {

    private String id;
    private String firstName;
    private String lastName;
    private int cluster;
    private String country;
    private String image;
    private FriendRequestDto friendRequest;

    public InterfaceUserDto() {
    }

    public InterfaceUserDto(String id, String firstName, String lastName, String image, String country, FriendRequestDto friendRequest){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.image = image;
        this.country = country;
        this.friendRequest = friendRequest;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setFriendRequest(FriendRequestDto friendRequest) {
        this.friendRequest = friendRequest;
    }

    public FriendRequestDto getFriendRequest() {
        return friendRequest;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setCluster(int cluster) {
        this.cluster = cluster;
    }

    public int getCluster() {
        return cluster;
    }
}
