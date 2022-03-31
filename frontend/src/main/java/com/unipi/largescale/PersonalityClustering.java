package com.unipi.largescale;
import com.unipi.largescale.API.API;
import com.unipi.largescale.util.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Properties;
public class PersonalityClustering extends Application {
    public static Stage stage;

    public void start(Stage stage){
        ConfigurationParameters configurationParameters = new ConfigurationParameters("src/main/resources/configurationParameters.xml");
        API.setConfiguration(configurationParameters);
        Properties props = System.getProperties();
        props.setProperty("javafx.platform", "Desktop");
        System.out.println("Loading the login page");
        FXMLLoader loader = new FXMLLoader(PersonalityClustering.class.getResource("/login.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        assert root != null;
        Scene scene = new Scene(root);
        stage.setScene(scene);
        PersonalityClustering.stage = stage;
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
