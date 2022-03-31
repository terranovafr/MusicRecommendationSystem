package com.unipi.largescale.entities;

import com.unipi.largescale.beans.UserBean;
import com.unipi.largescale.dtos.InterfaceUserDto;
import com.unipi.largescale.dtos.UserDto;

import java.time.LocalDate;

public class User {
    private String id;
    private String image;
    private String fullName;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String phoneNumber;
    private String password;
    private String gender;
    private LocalDate dateOfBirth;
    private LocalDate registrationDate;
    private String country;
    private double agreeableness;
    private double extraversion;
    private double neuroticism;
    private double openness;
    private double conscientiousness;
    private double timeSpent;
    private int cluster;
    private boolean admin;
    private FriendRequest friendRequest;

    public User(String id) {
        this.id = id;
    }

    public User(String firstName, String lastName, String username, String email, String phoneNumber, String password, String gender, LocalDate dateOfBirth, LocalDate registrationDate, String country, String url){
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.country = country;
        this.image = url;
        this.registrationDate = registrationDate;
    }

    public User(String email, String password){
        this.email = email;
        this.password = password;
    }

    public User(InterfaceUserDto neo4jUser){
        this.id = neo4jUser.getId();
        this.firstName = neo4jUser.getFirstName();
        this.lastName = neo4jUser.getLastName();
        this.image = neo4jUser.getImage();
        this.country = neo4jUser.getCountry();
        if (neo4jUser.getFriendRequest() != null) {
            this.friendRequest = new FriendRequest(neo4jUser.getFriendRequest());
        }
    }

    public boolean getAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public User(UserDto userDto){
        this.id = userDto.getId();
        this.firstName = userDto.getFirstName();
        this.lastName = userDto.getLastName();
        this.username = userDto.getUsername();
        this.email = userDto.getEmail();
        this.phoneNumber = userDto.getPhoneNumber();
        this.password = userDto.getPassword();
        this.gender = userDto.getGender();
        this.dateOfBirth = userDto.getDateOfBirth();
        this.country = userDto.getCountry();
        this.image = userDto.getImage();
        this.registrationDate = userDto.getRegistrationDate();
        this.fullName = this.firstName + " " + this.lastName;
        this.cluster = userDto.getCluster();
        this.agreeableness = userDto.getAgreeableness();
        this.neuroticism = userDto.getNeuroticism();
        this.extraversion = userDto.getExtraversion();
        this.conscientiousness = userDto.getConscientiousness();
        this.openness = userDto.getOpenness();
        this.timeSpent = userDto.getTimeSpent();
        this.admin = userDto.getAdmin();
    }

    public User(UserBean userBean){
        this.id = userBean.getId();
        this.firstName = userBean.getFirstName();
        this.lastName = userBean.getLastName();
        this.username = userBean.getUsername();
        this.email = userBean.getEmail();
        this.phoneNumber = userBean.getPhoneNumber();
        this.password = userBean.getPassword();
        this.gender = userBean.getGender();
        this.dateOfBirth = userBean.getDateOfBirth();
        this.country = userBean.getCountry();
        this.fullName = this.firstName + " " + this.lastName;
        this.cluster = userBean.getCluster();
    }

    public int getCluster() {
        return cluster;
    }

    public void setCluster(int cluster) {
        this.cluster = cluster;
    }

    public void setImage(String url) {
        this.image = url;
    }

    public String getImage() {
        return image;
    }

    public double getAgreeableness(){
        return agreeableness;
    }

    public double getOpenness(){
        return openness;
    }

    public double getExtraversion(){
        return extraversion;
    }

    public double getTimeSpent(){
        return timeSpent;
    }

    public double getNeuroticism(){
        return neuroticism;
    }

    public double getConscientiousness(){
        return conscientiousness;
    }

    public void setAgreeableness(double agreeableness) {
        this.agreeableness = agreeableness;
    }

    public void setExtraversion(double extraversion) {
        this.extraversion = extraversion;
    }

    public void setConscientiousness(double conscientiousness) {
        this.conscientiousness = conscientiousness;
    }

    public void setOpenness(double openness) {
        this.openness = openness;
    }

    public void setTimeSpent(double timeSpent) {
        this.timeSpent = timeSpent;
    }

    public void setNeuroticism(double neuroticism) {
        this.neuroticism = neuroticism;
    }

    public void setFriendRequest(FriendRequest friendRequest) {
        this.friendRequest = friendRequest;
    }

    public FriendRequest getFriendRequest() {
        return friendRequest;
    }


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

}


