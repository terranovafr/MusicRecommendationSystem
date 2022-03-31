package com.unipi.largescale.gui;

import com.unipi.largescale.entities.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

import static com.unipi.largescale.service.UserService.*;


public class FXMLUserInfoDocumentController implements Initializable{
    @FXML
    private TextField name;
    @FXML
    private TextField surname;
    @FXML
    private TextField username;
    @FXML
    private TextField email;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField dateOfBirthInput;
    @FXML
    private TextField genderInput;
    @FXML
    private TextField countryInput;
    @FXML
    private ImageView image;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User user = getUserInfo(FXMLFriendshipsDocumentController.userInfoID);
        FXMLFriendshipsDocumentController.userInfoID = null;
        image.setImage(new Image(user.getImage()));
        name.setText(user.getFirstName());
        name.setEditable(false);
        surname.setText(user.getLastName());
        surname.setEditable(false);
        username.setText(user.getUsername());
        username.setEditable(false);
        email.setText(user.getEmail());
        email.setEditable(false);
        phoneNumber.setText(user.getPhoneNumber());
        phoneNumber.setEditable(false);
        genderInput.setText(user.getGender());
        genderInput.setEditable(false);
        dateOfBirthInput.setText(user.getDateOfBirth().toString());
        dateOfBirthInput.setEditable(false);
        countryInput.setText(user.getCountry());
        countryInput.setEditable(false);
    }
}
