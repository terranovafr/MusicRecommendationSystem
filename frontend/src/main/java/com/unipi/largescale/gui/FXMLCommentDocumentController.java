package com.unipi.largescale.gui;

import com.unipi.largescale.beans.CommentBean;
import com.unipi.largescale.entities.Comment;
import com.unipi.largescale.entities.Song;
import com.unipi.largescale.service.SongService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static com.unipi.largescale.service.SongService.*;

public class FXMLCommentDocumentController implements Initializable{
    @FXML
    private TableColumn<CommentBean, String> nameColumn;
    @FXML
    private TableColumn<CommentBean, String> surnameColumn;
    @FXML
    private TableColumn<CommentBean, String> textColumn;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextArea newComment;
    @FXML
    private TableView<CommentBean> tableView;

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

    @FXML
    private void commentSong(ActionEvent event){
        System.out.println("Commenting the song");
        String comment = newComment.getText();
        Comment userComment = SongService.commentSong(comment, new Song(FXMLSongInfoDocumentController.selectedSong));
        newComment.setText("");
        List<Comment> songComments = showComments(new Song(FXMLSongInfoDocumentController.selectedSong));
        ObservableList<CommentBean> commentBeans = FXCollections.observableArrayList();
        commentBeans.add(new CommentBean(userComment));
        if(songComments != null) {
            for (Comment songComment : songComments) {
                commentBeans.add(new CommentBean(songComment));
            }
        }
        tableView.setItems(commentBeans);

    }

    @FXML
    private void showAllComments(ActionEvent event){
        System.out.println("The user is requesting to show all comments of the song");
        List<Comment> songComments = SongService.showAllComments(new Song(FXMLSongInfoDocumentController.selectedSong));
        ObservableList<CommentBean> commentBeans = FXCollections.observableArrayList();
        if(songComments != null) {
            for (Comment comment : songComments) {
                commentBeans.add(new CommentBean(comment));
            }
        }
        tableView.setItems(commentBeans);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        textColumn.setCellValueFactory(new PropertyValueFactory<>("text"));
        List<Comment> songComments = showComments(new Song(FXMLSongInfoDocumentController.selectedSong));
        ObservableList<CommentBean> commentBeans = FXCollections.observableArrayList();
        if(songComments != null) {
            for (Comment comment : songComments) {
                commentBeans.add(new CommentBean(comment));
            }
        }
        tableView.setItems(commentBeans);
    }
}
