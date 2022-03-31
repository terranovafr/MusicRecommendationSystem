package com.unipi.largescale.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import java.net.URL;
import java.util.ResourceBundle;
import static com.unipi.largescale.gui.ValidationForm.*;

import static com.unipi.largescale.service.UserService.*;

public class FXMLLoginDocumentController implements Initializable{
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;

    @FXML
    private void login(ActionEvent event) {
        String loginEmail = email.getText();
        String loginPassword = password.getText();
        password.setStyle("-fx-border-color: null;");
        email.setStyle("-fx-border-color: null;");
        System.out.println("The user tried to login");
        if(!validEmailAddress(loginEmail)) {
            email.setStyle("-fx-border-color: #FF0000;");
            return;
        }
        if(!validPassword(loginPassword)){
            password.setStyle("-fx-border-color: #FF0000;");
            return;
        }
        try {
            loginUser(loginEmail, loginPassword);
        } catch(Exception e) {
            System.out.println("Login not valid: " + e.getMessage());
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Login not valid");
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
            return;
        }
        System.out.println("User logged in succesfully!");
        LoaderFXML object = new LoaderFXML();
        Pane layout;
        FXMLHomeDocumentController.admin = user.getAdmin();
        if(FXMLHomeDocumentController.admin)
            layout = object.getPage("layoutAdmin");
        else layout = object.getPage("layout");
        try {
            anchorPane.getChildren().clear();
            anchorPane.getChildren().add(layout);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void register(ActionEvent event){
        System.out.println("Loading the registration form");
        LoaderFXML object = new LoaderFXML();
        Pane registerPane = object.getPage("register");
        try {
            anchorPane.getChildren().clear();
            anchorPane.getChildren().add(registerPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
