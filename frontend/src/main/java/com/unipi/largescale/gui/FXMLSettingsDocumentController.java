package com.unipi.largescale.gui;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.unipi.largescale.PersonalityClustering;
import com.unipi.largescale.entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;
import java.util.ResourceBundle;

import static com.unipi.largescale.service.UserService.*;
import static com.unipi.largescale.gui.ValidationForm.validEmailAddress;
import static com.unipi.largescale.util.UtilGUI.readListOfCountries;


public class FXMLSettingsDocumentController implements Initializable{
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
    private PasswordField password;
    @FXML
    private ChoiceBox<String> gender;
    @FXML
    private ChoiceBox<String> country;
    @FXML
    private ImageView image;

    @FXML
    private void uploadPic(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(PersonalityClustering.stage);
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dslbv6rz5",
                "api_key", "628347138896939",
                "api_secret", "k9zyTI4iM2nbYvQWLDDAn-18KBs",
                "secure", true));
        Map uploadedImage = cloudinary.uploader().upload(selectedFile, Collections.emptyMap());
        String imageUrl = uploadedImage.get("secure_url").toString();
        image.setImage(new Image(imageUrl));
        System.out.println("User uploaded a new profile picture");
    }

    @FXML
    private void saveSettings(ActionEvent event) {
        String newName = name.getText();
        String newSurname = surname.getText();
        String newUsername = username.getText();
        String newEmail = email.getText();
        String newPhoneNumber = phoneNumber.getText();
        String newPassword = password.getText();
        String newCountry = country.getValue();
        String newGender = gender.getValue();
        LocalDate newDateOfBirth = null;
        String newImage = image.getImage().getUrl();
        name.setStyle("-fx-border-color: null;");
        surname.setStyle("-fx-border-color: null;");
        username.setStyle("-fx-border-color: null;");
        country.setStyle("-fx-border-color: null;-fx-background-color:#CCE5FF");
        gender.setStyle("-fx-border-color: null;-fx-background-color: #CCE5FF");
        password.setStyle("-fx-border-color: null;");
        email.setStyle("-fx-border-color: null;");
        phoneNumber.setStyle("-fx-border-color: null;");
        if(newName.equals("")) {
            name.setStyle("-fx-border-color: #FF0000;");
            return;
        }
        if(newSurname.equals("")) {
            surname.setStyle("-fx-border-color: #FF0000;");
            return;
        }
        if(newUsername.equals("")) {
            username.setStyle("-fx-border-color: #FF0000;");
            return;
        }
        if(!validEmailAddress(newEmail)) {
            email.setStyle("-fx-border-color: #FF0000;");
            return;
        }
        if(newGender == null) {
            gender.setStyle("-fx-border-color: #FF0000;-fx-background-color: #CCE5FF;");
            return;
        }
        if(newCountry == null) {
            country.setStyle("-fx-border-color: #FF0000;-fx-background-color: #CCE5FF;");
            return;
        }
        try {
            updateUserInfo(new User(newName, newSurname, newUsername, newEmail, newPhoneNumber, newPassword, newGender, newDateOfBirth, null, newCountry, newImage));
            System.out.println("New settings saved!");
        } catch(Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            System.out.println("Error in saving settings: " + e.getMessage());
            errorAlert.setHeaderText("Information not valid!");
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
            name.setText(user.getFirstName());
            surname.setText(user.getLastName());
            username.setText(user.getUsername());
            email.setText(user.getEmail());
            phoneNumber.setText(user.getPhoneNumber());
            country.getItems().addAll(readListOfCountries());
            country.setValue(user.getCountry());
            gender.getItems().add("M");
            gender.getItems().add("F");
            gender.getItems().add("Not Specified");
            gender.setValue(user.getGender());
            image.setImage(new Image(user.getImage()));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.setText(user.getFirstName());
        surname.setText(user.getLastName());
        username.setText(user.getUsername());
        email.setText(user.getEmail());
        phoneNumber.setText(user.getPhoneNumber());
        country.getItems().addAll(readListOfCountries());
        country.setValue(user.getCountry());
        gender.getItems().add("M");
        gender.getItems().add("F");
        gender.getItems().add("Not Specified");
        gender.setValue(user.getGender());
        image.setImage(new Image(user.getImage()));
    }
}
