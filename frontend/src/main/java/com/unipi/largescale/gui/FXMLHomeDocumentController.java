package com.unipi.largescale.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import java.net.URL;
import java.util.ResourceBundle;
import static com.unipi.largescale.service.UserService.*;


public class FXMLHomeDocumentController implements Initializable{
    private boolean initialized;
    public static boolean admin;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private AnchorPane layoutPane;

    @FXML
    private void showRecommendedSongs(ActionEvent event){
        System.out.println("Loading the recommended songs page");
        LoaderFXML object = new LoaderFXML();
        Pane homePane = object.getPage("recommendedSongs");
        try {
            if(layoutPane != null)
                layoutPane.getChildren().clear();
            assert layoutPane != null;
            layoutPane.getChildren().add(homePane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showRecommendedUsers(ActionEvent event){
        System.out.println("Loading the recommended friends page");
        LoaderFXML object = new LoaderFXML();
        Pane homePane = object.getPage("recommendedUsers");
        try {
            if(layoutPane != null)
                layoutPane.getChildren().clear();
            assert layoutPane != null;
            layoutPane.getChildren().add(homePane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showFriendships(ActionEvent event){
        System.out.println("Showing the friendships of the user");
        LoaderFXML object = new LoaderFXML();
        Pane homePane = object.getPage("friends");
        try {
            if(layoutPane != null)
                layoutPane.getChildren().clear();
            assert layoutPane != null;
            layoutPane.getChildren().add(homePane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showUsersAdminPage(ActionEvent event){
        System.out.println("Showing the users page to the admin");
        LoaderFXML object = new LoaderFXML();
        Pane homePane = object.getPage("usersAdmin");
        try {
            if(layoutPane != null)
                layoutPane.getChildren().clear();
            assert layoutPane != null;
            layoutPane.getChildren().add(homePane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showStatsAdminPage(ActionEvent event){
        System.out.println("Showing the stats page to the admin");
        LoaderFXML object = new LoaderFXML();
        Pane homePane = object.getPage("statsAdmin");
        try {
            if(layoutPane != null)
                layoutPane.getChildren().clear();
            assert layoutPane != null;
            layoutPane.getChildren().add(homePane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void searchSong(ActionEvent event){
        System.out.println("Showing the search songs page");
        LoaderFXML object = new LoaderFXML();
        Pane homePane = object.getPage("searchSongs");
        try {
            if(layoutPane != null)
                layoutPane.getChildren().clear();
            assert layoutPane != null;
            layoutPane.getChildren().add(homePane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showSongsAdminPage(ActionEvent event){
        System.out.println("Showing the songs to the admin");
        LoaderFXML object = new LoaderFXML();
        Pane homePane = object.getPage("songsAdmin");
        try {
            if(layoutPane != null)
                layoutPane.getChildren().clear();
            assert layoutPane != null;
            layoutPane.getChildren().add(homePane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void logout(ActionEvent event){
        System.out.println("The user is logging out");
        LoaderFXML object = new LoaderFXML();
        Pane loginPane = object.getPage("login");
        logoutUser();
        try {
            anchorPane.getChildren().clear();
            anchorPane.getChildren().add(loginPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showSettings(ActionEvent event){
        System.out.println("Loading the settings page");
        LoaderFXML object = new LoaderFXML();
        Pane settingsPane = object.getPage("settings");
        try {
            layoutPane.getChildren().clear();
            layoutPane.getChildren().add(settingsPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showStats(ActionEvent event){
        System.out.println("Loading the stats page");
        LoaderFXML object = new LoaderFXML();
        Pane statsPane = object.getPage("stats");
        try {
            layoutPane.getChildren().clear();
            layoutPane.getChildren().add(statsPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showFriendRequests(ActionEvent event){
        System.out.println("Loading the friend requests of the user");
        LoaderFXML object = new LoaderFXML();
        Pane friendPane = object.getPage("friendRequests");
        try {
            if(layoutPane != null)
                layoutPane.getChildren().clear();
            assert layoutPane != null;
            layoutPane.getChildren().add(friendPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(!initialized && layoutPane != null){
            if(!admin)
                showRecommendedSongs(new ActionEvent());
            else showSongsAdminPage(new ActionEvent());
            initialized = true;
        }
    }
}
