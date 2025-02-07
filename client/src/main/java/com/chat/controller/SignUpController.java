package com.chat.controller;

import com.chat.network.ServerRepository;
import com.chat.utils.Director;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import com.chat.utils.UserValidation;

import java.io.File;

import javafx.event.*;

public class SignUpController {
  @FXML
  private PasswordField userPassword;

  @FXML
  private ImageView imageViewSignUp;

  @FXML
  private PasswordField userConfirmPassword;

  @FXML
  private Hyperlink signInNavigation;

  @FXML
  private Label UserEmailLabel;

  @FXML
  private TextField userBio;

  @FXML
  private TextField userPhoneNumber;

  @FXML
  private ImageView welcomeImageView;

  @FXML
  private TextField userName;

  @FXML
  private Label GenderLabel;

  @FXML
  private Label BioLabel;

  @FXML
  private Label passwordLabel;

  @FXML
  private TextField userCountry;

  @FXML
  private DatePicker userBirthDate;

  @FXML
  private Button addProfilePictureButton;

  @FXML
  private Label UserNameLabel;

  @FXML
  private Label confirmPasswordLabel;

  @FXML
  private Label UserNumberLabel;

  @FXML
  private Button signUpButton;

  @FXML
  private TextField userEmail;

  @FXML
  private TextField userGender;

  @FXML
  private Label CountryLabel;

  @FXML
  public void initialize() {

    userPhoneNumber.focusedProperty().addListener((observable, oldValue, newValue) -> {

      if (!newValue)
        isValidPhoneNumber();

    });

    userName.focusedProperty().addListener((observable, oldValue, newValue) -> {

      if (!newValue)
        isValidName();

    });

    userEmail.focusedProperty().addListener((observable, oldValue, newValue) -> {

      if (!newValue)
        isValidEmail();

    });

    userPassword.focusedProperty().addListener((observable, oldValue, newValue) -> {

      if (!newValue)
        isValidPassword();

    });

  userConfirmPassword.focusedProperty().addListener((observable, oldValue, newValue) -> {

    if (!newValue)
      isValidConfirmPassword();
      
        });

    userGender.focusedProperty().addListener((observable, oldValue, newValue) -> {

      if (!newValue)
        isValidGender();
                
                  });

    userCountry.focusedProperty().addListener((observable, oldValue, newValue) -> {

      if (!newValue)
        isValidCountry();
                        
                          });
                        
                        }
                      
                        private void isValidCountry() {
            String country =  userCountry.getText();

            boolean IsValidCountry = UserValidation.isValidCountry(country);

            if (!IsValidCountry) {
              CountryLabel.setVisible(true);
        
              CountryLabel.setText("Invalid");
        
              CountryLabel.setStyle("-fx-text-fill: red;");
            } else {
              CountryLabel.setVisible(false);
            }

          }
        
                        private void isValidGender() {
           
                  String gender = userGender.getText();

                  boolean IsValidPassword = UserValidation.isValidGender(gender);

    if (!IsValidPassword) {
      GenderLabel.setVisible(true);

      GenderLabel.setText("Invalid");

      GenderLabel.setStyle("-fx-text-fill: red;");
    } else {
      GenderLabel.setVisible(false);
    }


                  
          }
        
                private void isValidConfirmPassword() {
         
          String confirmPassword= userConfirmPassword.getText();

          String password = userPassword.getText();


          boolean IsValidPassword =   confirmPassword.equals(password);

          if (!IsValidPassword) {
            confirmPasswordLabel.setVisible(true);
      
            confirmPasswordLabel.setText("Invalid");
      
            confirmPasswordLabel.setStyle("-fx-text-fill: red;");
          } else {
            confirmPasswordLabel.setVisible(false);
          }


        }
      
        private void isValidPassword() {

    String name = userPassword.getText();

    boolean IsValidPassword = UserValidation.isValidPassword(name);

    if (!IsValidPassword) {
      passwordLabel.setVisible(true);

      passwordLabel.setText("Invalid");

      passwordLabel.setStyle("-fx-text-fill: red;");
    } else {
      passwordLabel.setVisible(false);
    }

  }

  private void isValidEmail() {

    String name = userEmail.getText();

    boolean isValidEmail = UserValidation.isVaildEmail(name);

    if (!isValidEmail) {
      UserEmailLabel.setVisible(true);

      UserEmailLabel.setText("Invalid");

      UserEmailLabel.setStyle("-fx-text-fill: red;");
    } else {
      UserEmailLabel.setVisible(false);
    }

  }

  private void isValidName() {
    String name = userName.getText();

    boolean IsValidName = UserValidation.isVaildName(name);

    if (!IsValidName) {
      UserNameLabel.setVisible(true);

      UserNameLabel.setText("Invalid");

      UserNameLabel.setStyle("-fx-text-fill: red;");
    } else {
      UserNameLabel.setVisible(false);
    }
  }

  private void isValidPhoneNumber() {
    String phoneNumber = userPhoneNumber.getText();

    boolean isValidNumber = UserValidation.isValidPhoneNumber(phoneNumber);

    if (!isValidNumber) {
      UserNumberLabel.setVisible(true);

      UserNumberLabel.setText("Invalid");

      UserNumberLabel.setStyle("-fx-text-fill: red;");
    } else {
      UserNumberLabel.setVisible(false);
    }

  }

  @FXML
  void addNewUser(ActionEvent event) {


    //check all true
    //
    // get the user_id




  }

  @FXML
  void goToSignin(ActionEvent event) {


      try {
          //ClientStarter.setRoot("login");
        Director.loadView(event, "login");
      } catch (Exception e) {
          throw new RuntimeException(e);
      }

  }

  @FXML
  void addPhoto(ActionEvent event) {

    FileChooser fileChooser = new FileChooser();
    File selectedFile = fileChooser.showOpenDialog(((Button) event.getSource()).getScene().getWindow());

    Image img = new Image(selectedFile.toURI().toString());

    imageViewSignUp.setImage(img);

  }

}
