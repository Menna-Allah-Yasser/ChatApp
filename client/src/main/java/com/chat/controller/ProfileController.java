package com.chat.controller;


import com.chat.network.ServerConnection;
import com.chat.network.ServerRepository;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.chat.entity.User;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.ZoneId;

public class ProfileController {
    @FXML
    private Label welcomeText;

    @FXML
    private ImageView imgView;

    @FXML
    private DatePicker DOB;

    @FXML
    private TextField bio;

    @FXML
    private MenuButton country;

    @FXML
    private TextField email;

    @FXML
    private MenuButton gender;

    @FXML
    private MenuButton mode;

    @FXML
    private TextField modehead;

    @FXML
    private TextField name;

    @FXML
    private TextField phone;

    User user ;
    private ServerRepository serverRepository = ServerConnection.getServer();
    @FXML
    public void initialize() {
        showUserData();
    }
    @FXML
    void updateUserProfile() {


        try {

            user = serverRepository.getUser(1);
            System.out.println(user);
            user.setEmail(email.getText());
            user.setBio(bio.getText());
            user.setDob(DOB.getValue());
            user.setMode(mode.getText());
            user.setGender(gender.getText());
            user.setCountry(country.getText());
            serverRepository.updateUserInfo(user);
            showUserData();
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    void updateImg() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Profile Image");

        // Set file extension filters (images only)
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        // Show open file dialog
        File selectedFile = fileChooser.showOpenDialog(imgView.getScene().getWindow());

        if (selectedFile != null) {
            try {
                // Convert image file to byte array
                byte[] imageBytes = Files.readAllBytes(selectedFile.toPath());

                // Example user ID, replace with the actual user ID
                int userId = 1;

                // Call the server method to update the image
                serverRepository.updateUserImage(userId, imageBytes);

                Image newImage = new Image(selectedFile.toURI().toString());
                imgView.setImage(newImage);

                // Optional: Show success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Profile Image Updated");
                alert.showAndWait();

            } catch (IOException e) {
                e.printStackTrace();

                // Show error message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Failed to upload image");
                alert.setContentText("An error occurred while reading the image file.");
                alert.showAndWait();
            }
        }
    }


    private void showUserData(){
        try {
            User user = serverRepository.getUser(1);
            name.setText(user.getName());
            modehead.setText(user.getMode());
            phone.setText(user.getPhoneNumber());
            bio.setText(user.getBio());
            country.setText(user.getCountry());
            gender.setText(user.getGender());
            mode.setText(user.getMode());
            email.setText(user.getEmail());
            DOB.setValue(user.getDob());
//            byte[] imageData  = user.getPicture();
//            InputStream inputStream = new ByteArrayInputStream(imageData);
//            Image newImage = new Image(inputStream);
//            imgView.setImage(newImage);

        }catch(RemoteException e) {
            System.out.println(e.getMessage());
        }
    }

}