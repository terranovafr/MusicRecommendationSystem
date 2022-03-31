package com.unipi.largescale.gui;

import com.unipi.largescale.beans.UserBean;
import com.unipi.largescale.entities.User;
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

import static com.unipi.largescale.service.UserService.*;



public class FXMLFriendshipsDocumentController implements Initializable{
    @FXML
    private TableColumn<UserBean, ImageView> imageColumn;
    @FXML
    private TableColumn<UserBean, String> firstNameColumn;
    @FXML
    private TableColumn<UserBean, String> lastNameColumn;
    @FXML
    private TableColumn<UserBean, String> infoColumn;
    @FXML
    private AnchorPane friendsPane;
    @FXML
    private TableView<UserBean> tableView;
    public static String userInfoID;

    @FXML
    private void showUserInfo(ActionEvent event){
        System.out.println("Showing user information");
        LoaderFXML object = new LoaderFXML();
        Pane userPane = object.getPage("userInfo");
        try {
            if(friendsPane != null)
                friendsPane.getChildren().clear();
            assert friendsPane != null;
            friendsPane.getChildren().add(userPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        Callback<TableColumn<UserBean, String>, TableCell<UserBean, String>> cellFactory
                = //
                new Callback<>() {
                    @Override
                    public TableCell call(final TableColumn<UserBean, String> param) {
                        return new TableCell<UserBean, String>() {

                            final Button btn = new Button();

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                } else {
                                    UserBean person = getTableView().getItems().get(getIndex());
                                    btn.setStyle("-fx-background-color: #CCE5FF");
                                    btn.setText("Show information");
                                    btn.setOnAction(event -> {
                                        userInfoID = person.getId();
                                        showUserInfo(event);
                                    });
                                    setGraphic(btn);
                                }
                                setText(null);
                            }
                        };
                    }
                };
        infoColumn.setCellFactory(cellFactory);
        ObservableList<UserBean> userBeans = FXCollections.observableArrayList();
        List<User> friends = getFriendships();
        if(friends != null) {
            for (User user: friends) {
                userBeans.add(new UserBean(user));
            }
        }
        tableView.setItems(userBeans);
    }
}
