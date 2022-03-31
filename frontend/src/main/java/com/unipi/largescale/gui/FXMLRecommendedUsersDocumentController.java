package com.unipi.largescale.gui;

import com.unipi.largescale.beans.UserBean;
import com.unipi.largescale.entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static com.unipi.largescale.service.UserService.*;


public class FXMLRecommendedUsersDocumentController implements Initializable{
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TableView<UserBean> tableView;
    @FXML
    private TableColumn<UserBean, ImageView> imageColumn;
    @FXML
    private TableColumn<UserBean, String> firstNameColumn;
    @FXML
    private TableColumn<UserBean, String> lastNameColumn;
    @FXML
    private TableColumn<UserBean, String> sendRequestColumn;

    @FXML
    private void showNearbyUsers(ActionEvent event){
        System.out.println("Showing nearby users to the user");
        ObservableList<UserBean> userBeans = FXCollections.observableArrayList();
        List<User> similarUsers = getNearbyUsers();
        if(similarUsers != null) {
            for (User similarUser : similarUsers) {
                userBeans.add(new UserBean(similarUser));
            }
        }
        tableView.setItems(userBeans);
    }

    @FXML
    private void showRecommendedUsers(ActionEvent event){
        System.out.println("Loading the recommended friends page");
        LoaderFXML object = new LoaderFXML();
        Pane homePane = object.getPage("recommendedUsers");
        try {
            if(anchorPane != null)
                anchorPane.getChildren().clear();
            assert anchorPane != null;
            anchorPane.getChildren().add(homePane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void checkForUpdates(ActionEvent event){
        System.out.println("Checking for updates...");
        checkForNewRecommended();
        showRecommendedUsers(new ActionEvent());
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
                            final Button btn = new Button("Send Request");
                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                } else {
                                    btn.setStyle("-fx-background-color: #CCE5FF");
                                    btn.setOnAction(event -> {
                                        UserBean person = getTableView().getItems().get(getIndex());
                                        // if request not sent already
                                        if(!person.getRequested()) {
                                            addFriendRequest(person.getId());
                                            System.out.println("User sent a friend request");
                                            btn.setDisable(true);
                                            btn.setText("Request sent");
                                        } else {
                                            //request already sent
                                            System.out.println("User requested a request already sent");
                                            btn.setDisable(true);
                                            btn.setText("Request already sent");
                                        }
                                    });
                                    setGraphic(btn);
                                }
                                setText(null);
                            }
                        };
                    }
                };
        sendRequestColumn.setCellFactory(cellFactory);
        List<User> similarUsers = getSimilarUsers();
        ObservableList<UserBean> userBeans = FXCollections.observableArrayList();
        if(similarUsers != null) {
            for (User similarUser : similarUsers) {
                userBeans.add(new UserBean(similarUser));
            }
            tableView.setItems(userBeans);
        }
    }
}
