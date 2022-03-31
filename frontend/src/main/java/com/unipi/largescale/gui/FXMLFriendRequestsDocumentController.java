package com.unipi.largescale.gui;

import com.unipi.largescale.beans.UserBean;
import com.unipi.largescale.entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static com.unipi.largescale.service.UserService.*;


public class FXMLFriendRequestsDocumentController implements Initializable{
    @FXML
    private TableView<UserBean> tableView;
    @FXML
    private TableColumn<UserBean, ImageView> imageColumn;
    @FXML
    private TableColumn<UserBean, String> firstNameColumn;
    @FXML
    private TableColumn<UserBean, String> lastNameColumn;
    @FXML
    private TableColumn<UserBean, String> declineRequestColumn;
    @FXML
    private TableColumn<UserBean, String> acceptRequestColumn;

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
                            final Button btn = new Button("Accept");
                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                } else {
                                    btn.setStyle("-fx-background-color: #CCE5FF");
                                    btn.setOnAction(event -> {
                                        UserBean person = getTableView().getItems().get(getIndex());
                                        acceptFriendRequest(new User(person));
                                        System.out.println("User accepted a friendship request");
                                        List<User> friendRequests = getFriendshipRequests();
                                        ObservableList<UserBean> userBeans = FXCollections.observableArrayList();
                                        for(User friendRequesting: friendRequests)
                                            userBeans.add(new UserBean(friendRequesting));
                                        tableView.setItems(userBeans);
                                    });
                                    setGraphic(btn);
                                }
                                setText(null);
                            }
                        };
                    }
                };
        acceptRequestColumn.setCellFactory(cellFactory);
        cellFactory = new Callback<>() {
            @Override
            public TableCell call(final TableColumn<UserBean, String> param) {
                return new TableCell<UserBean, String>() {
                    final Button btn = new Button("Decline");
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            btn.setStyle("-fx-background-color: #CCE5FF");
                            btn.setOnAction(event -> {
                                UserBean person = getTableView().getItems().get(getIndex());
                                deleteFriendRequest(new User(person));
                                System.out.println("User declined a friendship request");
                                List<User> friendRequests = getFriendshipRequests();
                                ObservableList<UserBean> userBeans = FXCollections.observableArrayList();
                                for(User friendRequesting: friendRequests)
                                    userBeans.add(new UserBean(friendRequesting));
                                tableView.setItems(userBeans);
                            });
                            setGraphic(btn);
                        }
                        setText(null);
                    }
                };
            }
        };
        declineRequestColumn.setCellFactory(cellFactory);
        List<User> friendRequests = getFriendshipRequests();
        ObservableList<UserBean> userBeans = FXCollections.observableArrayList();
        for(User friendRequesting: friendRequests)
            userBeans.add(new UserBean(friendRequesting));
        tableView.setItems(userBeans);
    }
}
