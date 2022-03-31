package com.unipi.largescale.gui;

import com.unipi.largescale.beans.SongBean;
import com.unipi.largescale.entities.Song;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static com.unipi.largescale.service.SongService.*;

public class FXMLSongsAdminDocumentController implements Initializable{
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField songTitle;
    @FXML
    private TableColumn<SongBean, String> nameColumn;
    @FXML
    private TableColumn<SongBean, String> artistColumn;
    @FXML
    private TableColumn<SongBean, String> deleteColumn;
    @FXML
    private TableColumn<SongBean, ImageView> imageColumn;
    @FXML
    private TableView<SongBean> tableView;

    @FXML
    private void searchSong(ActionEvent event){
        String name = songTitle.getText();
        List<Song> songs = getSongsByName(name);
        updateTable(songs);
    }

    private void updateTable(List<Song> songs){
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artists"));
        Callback<TableColumn<SongBean, String>, TableCell<SongBean, String>> cellFactory
                = //
                new Callback<>() {
                    @Override
                    public TableCell call(final TableColumn<SongBean, String> param) {
                        return new TableCell<SongBean, String>() {
                            final Button btn = new Button("Delete");
                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                } else {
                                    btn.setStyle("-fx-background-color: #CCE5FF");
                                    btn.setOnAction(event -> {
                                        SongBean song = getTableView().getItems().get(getIndex());
                                        deleteSong(new Song(song));
                                        System.out.println("The admin has deleted a song");
                                        searchSong(event);
                                    });
                                    setGraphic(btn);
                                }
                                setText(null);
                            }
                        };
                    }
                };
        deleteColumn.setCellFactory(cellFactory);
        ObservableList<SongBean> songBeans = FXCollections.observableArrayList();
        if(songs != null) {
            for (Song song : songs) {
                songBeans.add(new SongBean(song));
            }
        }
        tableView.setItems(songBeans);
    }

    @FXML
    private void addSong(ActionEvent event){
        System.out.println("Adding a new song");
        LoaderFXML object = new LoaderFXML();
        Pane homePane = object.getPage("newSongAdmin");
        try {
            if(anchorPane != null)
                anchorPane.getChildren().clear();
            assert anchorPane != null;
            anchorPane.getChildren().add(homePane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
