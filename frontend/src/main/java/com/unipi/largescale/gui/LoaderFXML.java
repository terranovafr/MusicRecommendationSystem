package com.unipi.largescale.gui;

import com.unipi.largescale.PersonalityClustering;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import java.net.URL;

public class LoaderFXML {
    private Pane view;

    public Pane getPage(String filename){
        try {
            URL fileUrl = PersonalityClustering.class.getResource("/"+filename+".fxml");
            if(fileUrl == null) {
                throw new java.io.FileNotFoundException("FXML file cannot be found");
            }
            FXMLLoader fxmlLoader = new FXMLLoader(fileUrl);
            view = fxmlLoader.load();
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        return view;
    }
}
