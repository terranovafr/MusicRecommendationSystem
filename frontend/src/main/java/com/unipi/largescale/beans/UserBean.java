package com.unipi.largescale.beans;

import com.unipi.largescale.entities.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.ImageView;
import java.time.LocalDate;
import java.util.Objects;

public class UserBean {
    private ImageView image;
    private final SimpleStringProperty id;
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private SimpleStringProperty username;
    private SimpleStringProperty email;
    private SimpleStringProperty phoneNumber;
    private SimpleStringProperty password;
    private SimpleStringProperty gender;
    private LocalDate dateOfBirth;
    private SimpleStringProperty country;
    private int cluster;
    private boolean requested;

    public void setId(String id) {
        this.id.set(id);
    }

    public UserBean(User user){
        this.firstName = new SimpleStringProperty(user.getFirstName());
        this.lastName = new SimpleStringProperty(user.getLastName());
        this.username = new SimpleStringProperty(user.getUsername());
        this.email = new SimpleStringProperty(user.getEmail());
        this.phoneNumber = new SimpleStringProperty(user.getPhoneNumber());
        this.password = new SimpleStringProperty(user.getPassword());
        this.gender = new SimpleStringProperty(user.getGender());
        this.dateOfBirth = user.getDateOfBirth();
        this.country = new SimpleStringProperty(user.getCountry());
        this.image = new ImageView(Objects.requireNonNullElse(user.getImage(), "https://villadewinckels.it/wp-content/uploads/2021/05/default-user-image.png"));
        this.id = new SimpleStringProperty(user.getId());
        this.cluster = user.getCluster();
        image.setFitHeight(30);
        image.setFitWidth(30);
        this.requested = (user.getFriendRequest() != null);
    }

    public void setRequested(boolean requested) {
        this.requested = requested;
    }

    public boolean getRequested() {
        return requested;
    }

    public int getCluster() {
        return cluster;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public ImageView getImage() {
        return image;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getCountry() {
        return country.get();
    }

    public String getGender() {
        return gender.get();
    }

    public String getLastName() {
        return lastName.get();
    }

    public String getEmail() {
        return email.get();
    }

    public String getPassword() {
        return password.get();
    }

    public String getFirstName() {
        return firstName.get();
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public String getUsername() {
        return username.get();
    }

    public String getId() {
        return id.get();
    }

}


