package com.unipi.largescale.gui;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.unipi.largescale.PersonalityClustering;
import com.unipi.largescale.entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import static com.unipi.largescale.gui.ValidationForm.*;
import static com.unipi.largescale.service.UserService.*;
import static com.unipi.largescale.util.UtilGUI.*;

public class FXMLRegisterDocumentController implements Initializable{
    private static int questionsAnswered;
    private static List<String> questions;
    @FXML
    private AnchorPane anchorPane;
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
    private DatePicker dateOfBirth;
    @FXML
    private ChoiceBox gender;
    @FXML
    private ChoiceBox country;
    @FXML
    private ImageView image;
    @FXML
    private Button answerButton;
    @FXML
    private Label question;
    @FXML
    private Slider slider;
    @FXML
    private BarChart personalityBarChart;
    @FXML
    private BarChart clusterBarChart;
    @FXML
    private Text personalityDescription;
    @FXML
    private Text clusterID;

    @FXML
    private void showHome(ActionEvent event) {
        System.out.println("Loading the home page");
        LoaderFXML object = new LoaderFXML();
        Pane layout = object.getPage("layout");
        try {
            anchorPane.getChildren().clear();
            anchorPane.getChildren().add(layout);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // method called each time a new question is answered
    @FXML
    private void answer(ActionEvent event) {
        double elapsedTime = (System.currentTimeMillis() - timeStart) / 1000;
        int answer;
        String nextQuestion;
        if (questionsAnswered < questions.size()) {
                answer = (int) slider.getValue();
                slider.setValue(3);
                try {
                    System.out.println("User answered the " + questionsAnswered + " question with " + answer + " in " + elapsedTime + " seconds.");
                    saveAnswer(questionsAnswered, answer, elapsedTime);
                } catch(Exception e){
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    System.out.println("Registration not valid: " + e.getMessage());
                    errorAlert.setHeaderText("Registration not valid");
                    errorAlert.setContentText(e.getMessage());
                    errorAlert.showAndWait();
                    LoaderFXML object = new LoaderFXML();
                    Pane layout = object.getPage("register");
                    try {
                        anchorPane.getChildren().clear();
                        anchorPane.getChildren().add(layout);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    return;
                }
                questionsAnswered++;
                timeStart = System.currentTimeMillis();
                // if the user still have to answer questions
                if(questionsAnswered < questions.size()) {
                    nextQuestion = questions.get(questionsAnswered);
                    question.setText(nextQuestion);
                    return;
                }
        }
        LoaderFXML object = new LoaderFXML();
        Pane statsRegister = object.getPage("statsRegister");
        try {
            anchorPane.getChildren().clear();
            anchorPane.getChildren().add(statsRegister);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void takeQuiz(ActionEvent event) {
        String registeredName = name.getText();
        String registeredSurname = surname.getText();
        String registeredUsername = username.getText();
        String registeredEmail = email.getText();
        String registeredPhoneNumber = phoneNumber.getText();
        String registeredPassword = password.getText();
        String registeredCountry = (String) country.getValue();
        String registeredGender = (String) gender.getValue();
        String registeredImage = null;
        if(image.getImage() != null)
            registeredImage = image.getImage().getUrl();
        LocalDate registeredDateOfBirth;
        registeredDateOfBirth = dateOfBirth.getValue();
        name.setStyle("-fx-border-color: null;");
        surname.setStyle("-fx-border-color: null;");
        username.setStyle("-fx-border-color: null;");
        country.setStyle("-fx-border-color: null;-fx-background-color:#CCE5FF");
        gender.setStyle("-fx-border-color: null;-fx-background-color: #CCE5FF");
        dateOfBirth.setStyle("-fx-border-color: null;");
        password.setStyle("-fx-border-color: null;");
        email.setStyle("-fx-border-color: null;");
        phoneNumber.setStyle("-fx-border-color: null;");
        if(registeredName.equals("")) {
            System.out.println("User fields are not correct");
            name.setStyle("-fx-border-color: #FF0000;");
            return;
        }
        if(registeredSurname.equals("")) {
            System.out.println("User fields are not correct");
            surname.setStyle("-fx-border-color: #FF0000;");
            return;
        }
        if(registeredUsername.equals("")) {
            System.out.println("User fields are not correct");
            username.setStyle("-fx-border-color: #FF0000;");
            return;
        }
        if(!validEmailAddress(registeredEmail)) {
            System.out.println("User fields are not correct");
            email.setStyle("-fx-border-color: #FF0000;");
            return;
        }
        if(!validPhoneNumber(registeredPhoneNumber)) {
            System.out.println("User fields are not correct");
            phoneNumber.setStyle("-fx-border-color: #FF0000;");
            return;
        }
        if(!validPassword(registeredPassword)){
            System.out.println("User fields are not correct");
            password.setStyle("-fx-border-color: #FF0000;");
            return;
        }
        if(registeredGender == null) {
            System.out.println("User fields are not correct");
            gender.setStyle("-fx-border-color: #FF0000;-fx-background-color: #CCE5FF;");
            return;
        }
        if(registeredDateOfBirth == null || !validDate(registeredDateOfBirth)){
            System.out.println("User fields are not correct");
            dateOfBirth.setStyle("-fx-border-color: #FF0000;");
            return;
        }
        if(registeredCountry == null) {
            System.out.println("User fields are not correct");
            country.setStyle("-fx-border-color: #FF0000;-fx-background-color: #CCE5FF;");
            return;
        }
        System.out.println("The user provided all correct fields");
        user = new User(registeredName, registeredSurname, registeredUsername, registeredEmail, registeredPhoneNumber, registeredPassword, registeredGender, registeredDateOfBirth, LocalDate.now(), registeredCountry, registeredImage);
        LoaderFXML object = new LoaderFXML();
        System.out.println("Loading the survey");
        Pane registerPane = object.getPage("survey");
        try {
            anchorPane.getChildren().clear();
            anchorPane.getChildren().add(registerPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
        questionsAnswered = 0;
    }

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
        System.out.println("User uploaded the profile picture.");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Registration Form Page
        if(answerButton != null){
            image.setImage(new Image("https://villadewinckels.it/wp-content/uploads/2021/05/default-user-image.png"));
            country.getItems().addAll(readListOfCountries());
            gender.getItems().add("M");
            gender.getItems().add("F");
            gender.getItems().add("Not Specified");
        }
        // First question
        if(slider != null && questionsAnswered == 0){
            if(questions == null)
                questions = getQuestions();
            question.setText(questions.get(0));
        }
        // Second question to the last
        if(slider != null){
            timeStart = System.currentTimeMillis();
        }
        // Stats Page
        if(personalityBarChart != null){
            System.out.println("Showing the stats to the user");
            personalityBarChart.setStyle("-fx-bar-fill: blue;");
            XYChart.Series<String, Number> values = new XYChart.Series<>();
            double[] personalityValues = getPersonalityValues();
            double[] clusterPersonalityValues = getClusterPersonalityValues();
            values.getData().add(new XYChart.Data<>("OPN", personalityValues[0]));
            values.getData().add(new XYChart.Data<>("AGR", personalityValues[1]));
            values.getData().add(new XYChart.Data<>("NSM", personalityValues[2]));
            values.getData().add(new XYChart.Data<>("EXT", personalityValues[3]));
            values.getData().add(new XYChart.Data<>("CNS", personalityValues[4]));
            values.getData().add(new XYChart.Data<>("Time", personalityValues[5]));
            personalityBarChart.getData().addAll(values);
            XYChart.Series<String, Number> valuesCluster = new XYChart.Series<>();
            if(clusterPersonalityValues != null) {
                valuesCluster.getData().add(new XYChart.Data<>("OPN", clusterPersonalityValues[0]));
                valuesCluster.getData().add(new XYChart.Data<>("AGR", clusterPersonalityValues[1]));
                valuesCluster.getData().add(new XYChart.Data<>("NSM", clusterPersonalityValues[2]));
                valuesCluster.getData().add(new XYChart.Data<>("EXT", clusterPersonalityValues[3]));
                valuesCluster.getData().add(new XYChart.Data<>("CNS", clusterPersonalityValues[4]));
                valuesCluster.getData().add(new XYChart.Data<>("Time", clusterPersonalityValues[5]));
            }
            clusterBarChart.getData().addAll(valuesCluster);
            personalityDescription.setText(getPersonalityDescription(user));
            clusterID.setText("Cluster " + getClusterID());
        }
    }
}
