package com.chat.controller;

import com.chat.ClientStarter;
import com.chat.entity.Chat;
import com.chat.entity.Participant;
import com.chat.entity.User;
import com.chat.network.ServerConnection;
import com.chat.network.ServerRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.BorderPane;


public class GroupController {

    @FXML
    private Button cancelButton;

    @FXML
    private Button createButton;

    @FXML
    private TextField groupName;

    @FXML
    private BorderPane mainPane;

    @FXML
    private ListView<User> userListView;

    private ServerRepository server = ServerConnection.getServer();;

    private final List<User> selectedUsers = new ArrayList<>();

    @FXML
    public void initialize() {
        System.out.println("Group init");

        server = ServerConnection.getServer();

        try {
            List<User> friends = server.getAllFriends(8);
            ObservableList<User> friendsList = FXCollections.observableArrayList(friends);
            userListView.setItems(friendsList);

            userListView.setFixedCellSize(80);

            userListView.setCellFactory(param -> new ListCell<User>() {
                @Override
                protected void updateItem(User user, boolean empty) {
                    super.updateItem(user, empty);
                    if (empty || user == null) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        ImageView imageView;
                        if (user.getPicture() != null && user.getPicture().length > 0) {
                            Image image = new Image(new ByteArrayInputStream(user.getPicture()));
                            imageView = new ImageView(image);
                        } else {
                            Image defaultImage = new Image(getClass().getResourceAsStream("/images/profile.png"));
                            imageView = new ImageView(defaultImage);
                        }
                        imageView.setFitHeight(40);
                        imageView.setFitWidth(40);

                        Label nameLabel = new Label(user.getName());
                        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

                        Label bioLabel = new Label(user.getBio() == null ? "" : user.getBio());
                        bioLabel.setStyle("-fx-text-fill: #666; -fx-font-size: 12px;");

                        CheckBox checkBox = new CheckBox();
                        checkBox.setOnAction(e -> {
                            if (checkBox.isSelected()) {
                                selectedUsers.add(user);
                            } else {
                                selectedUsers.remove(user);
                            }
                        });

                        // Spacer region to push the checkbox to the right
                        Region spacer = new Region();
                        HBox.setHgrow(spacer, Priority.ALWAYS);

                        HBox hBox = new HBox(10, imageView, new VBox(nameLabel, bioLabel), spacer, checkBox);
                        hBox.setPadding(new Insets(5));

                        setGraphic(hBox);
                    }
                }
            });


        } catch (RemoteException e) {
            System.out.println("Error fetching friends: " + e.getMessage());
        }
    }

    public List<User> getSelectedUsers() {
        return selectedUsers;
    }



    @FXML
    public void onCreateAction(ActionEvent actionEvent) {

        String msg = "";
        boolean gn = false , sz=false;

        if(groupName.getText().isEmpty()){
            msg+="you should Enter Group Name";
            gn=true;
        }
        if(selectedUsers.size()<2){
            msg+="\nGroup must have another 2 friends or more ";
            sz=true;
        }

        if(gn || sz) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setHeaderText(msg);
            alert.showAndWait();
        }

        try {
            selectedUsers.add(server.getUser(8));
            int chatId = server.addNewChat(new Chat(groupName.getText()));
            for(User user : selectedUsers){
                Participant participant = new Participant(chatId , user.getUserId() , Participant.State.AVAILABLE , Participant.Category.FRIEND);
                server.createParticpant(participant);
                System.out.println(participant);
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("GROUP Notification");
            alert.setHeaderText("GROUP " + groupName.getText()+" created successfully");
            alert.showAndWait();
            groupName.clear();

        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void onCancelAction() {
        System.out.println("Cancel action");

        try {
            FXMLLoader loader = new FXMLLoader(ClientStarter.class.getResource("bar.fxml"));
            Parent parent = loader.load();

            Scene currentScene = mainPane.getScene();
            currentScene.setRoot(parent);

            BarController barController = loader.getController();
            barController.onLogoClicked();

        } catch (IOException e) {
            System.err.println("Error loading view: " + e.getMessage());
            e.printStackTrace();
        } catch (IllegalStateException e) {
            System.err.println("Location not set error: " + e.getMessage());
            e.printStackTrace();
        }
    }







}
