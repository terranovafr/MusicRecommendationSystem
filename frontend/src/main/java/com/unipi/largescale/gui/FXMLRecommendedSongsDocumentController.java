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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static com.unipi.largescale.service.SongService.*;



public class FXMLRecommendedSongsDocumentController implements Initializable{
    @FXML
    private TableColumn<SongBean, String> nameColumn;
    @FXML
    private TableColumn<SongBean, String> artistColumn;
    @FXML
    private TableColumn<SongBean, String> likeColumn;
    @FXML
    private TableColumn<SongBean, String> unlikeColumn;
    @FXML
    private TableColumn<SongBean, String> showInfoColumn;
    @FXML
    private TableColumn<SongBean, ImageView> imageColumn;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TableView<SongBean> tableView;

    @FXML
    private void showSongInfo(ActionEvent event){
        System.out.println("Loading the song information page");
        LoaderFXML object = new LoaderFXML();
        Pane songInfoPane = object.getPage("songInfo");
        try {
            anchorPane.getChildren().clear();
            anchorPane.getChildren().add(songInfoPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artists"));
        Callback<TableColumn<SongBean, String>, TableCell<SongBean, String>> cellFactory
                = //
                new Callback<>() {
                @Override
                    public TableCell call(final TableColumn<SongBean, String> param) {
                    return new TableCell<SongBean, String>() {
                        final Button btn = new Button();
                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                } else {
                                    btn.setStyle("-fx-background-color: #CCE5FF");
                                    btn.setOnAction(event -> {
                                        SongBean song = getTableView().getItems().get(getIndex());
                                        likeSong(new Song(song));
                                        System.out.println("User liked a song");
                                        btn.setDisable(true);
                                    });
                                    Image img = new Image("https://cdn-icons-png.flaticon.com/512/126/126473.png");
                                    ImageView view = new ImageView(img);
                                    view.setFitHeight(10);
                                    view.setFitWidth(10);
                                    btn.setGraphic(view);
                                    setGraphic(btn);
                                }
                                setText(null);
                            }
                    };
                }
            };
        likeColumn.setCellFactory(cellFactory);
        cellFactory = //
                new Callback<>() {
                    @Override
                    public TableCell call(final TableColumn<SongBean, String> param) {
                        return new TableCell<SongBean, String>() {
                            final Button btn = new Button();
                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                } else {
                                    btn.setStyle("-fx-background-color: #CCE5FF");
                                    btn.setOnAction(event -> {
                                        SongBean song = getTableView().getItems().get(getIndex());
                                        unlikeSong(new Song(song));
                                        System.out.println("User unliked a song");
                                        btn.setDisable(true);
                                    });
                                    Image img = new Image("https://cdn-icons-png.flaticon.com/512/15/15107.png");
                                    ImageView view = new ImageView(img);
                                    view.setFitHeight(10);
                                    view.setFitWidth(10);
                                    btn.setGraphic(view);
                                    setGraphic(btn);
                                }
                                setText(null);
                            }
                        };
                    }
                };
        unlikeColumn.setCellFactory(cellFactory);
        cellFactory = //
                new Callback<>() {
                    @Override
                    public TableCell call(final TableColumn<SongBean, String> param) {
                        return new TableCell<SongBean, String>() {
                            final Button btn = new Button("Show info");
                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                } else {
                                    btn.setStyle("-fx-background-color: #CCE5FF");
                                    btn.setOnAction(event -> {
                                        FXMLSongInfoDocumentController.selectedSong = getTableView().getItems().get(getIndex());
                                        showSongInfo(event);
                                        System.out.println("User want more information about the song");
                                    });
                                    setGraphic(btn);
                                }
                                setText(null);
                            }
                        };
                    }
                };
        showInfoColumn.setCellFactory(cellFactory);
        List<Song> recommendedSongs = getRecommendedSongs();
        ObservableList<SongBean> songsBeans = FXCollections.observableArrayList();
        if(recommendedSongs != null) {
            for (Song song : recommendedSongs) {
                songsBeans.add(new SongBean(song));
            }
        }
        tableView.setItems(songsBeans);
    }
}
