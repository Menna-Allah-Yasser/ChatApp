package com.chat.controller;


import com.chat.ClientStarter;
import com.chat.entity.User;
import com.chat.network.ServerConnection;

import com.chat.network.ServerRepository;
import com.chat.utils.UserValidation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.Properties;


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
  private Label validUserLab;

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

  ServerRepository server;

  @FXML
  public void initialize() {
    signUpButton.setDisable(true);

    userPhoneNumber.focusedProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue) isValidPhoneNumber();
    });

    userName.focusedProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue) isValidName();
    });

    userEmail.focusedProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue) isValidEmail();
    });

    userPassword.focusedProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue) isValidPassword();
    });

    userConfirmPassword.focusedProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue) isValidConfirmPassword();
    });

    userGender.focusedProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue) isValidGender();
    });

    userCountry.focusedProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue) isValidCountry();
    });

    server = ServerConnection.getServer();
  }

  private void updateSignUpButtonState() {

      boolean allValid =
              false;
      try {
          allValid = UserValidation.isVaildName(userName.getText()) &&
                  UserValidation.isVaildEmail(userEmail.getText()) &&
                  UserValidation.isValidPassword(userPassword.getText()) &&
                  userPassword.getText().equals(userConfirmPassword.getText()) &&
                  UserValidation.isValidPhoneNumber(userPhoneNumber.getText()) &&
                  UserValidation.isValidGender(userGender.getText()) &&
                  UserValidation.isValidCountry(userCountry.getText())&&server.getUser(userPhoneNumber.getText())== null;
      } catch (RemoteException e) {
          throw new RuntimeException(e);
      }


      signUpButton.setDisable(!allValid);
  }

  private User CreateUser() throws IOException {
    User user = new User();

    user.setPhoneNumber(userPhoneNumber.getText());
    user.setEmail(userEmail.getText());
    user.setPassword(userPassword.getText());
    user.setName(userName.getText());
    user.setGender(userGender.getText());
    user.setCountry(userCountry.getText());
    user.setBio(userBio.getText());
    user.setDob(Date.valueOf(userBirthDate.getValue()).toLocalDate());
    user.setCountOfLogin(0);
    user.setIsChatbotEnabled(false);
    user.setFacebookUrl("www//null.com");
    user.setIsOnline(false);
    user.setTwitterUrl("www//null.com");
    user.setLinkedinUrl("www//null.com");
    user.setMode("busy");



    if (imageViewSignUp.getImage() == null) {
      InputStream inputStream = SignUpController.class.getResourceAsStream("/images/profile.png");
      if (inputStream == null) {
        throw new IOException("Image not found in resources!");
      }
      byte[] imageBytes = convertPhoto(inputStream);
      user.setPicture(imageBytes);
    } else {
      byte[] imageBytes = imageViewToBytes(imageViewSignUp);
      user.setPicture(imageBytes);


    }

    return user;
  }

  private byte[] convertPhoto(InputStream inputStream) throws IOException {

    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    byte[] buffer = new byte[1024];
    int bytesRead;

      while ((bytesRead = inputStream.read(buffer))!=-1)
      {
        byteArrayOutputStream.write(buffer ,0 ,bytesRead);
      }
    byte[] imageBytes = byteArrayOutputStream.toByteArray();

    System.out.println("Image converted to bytes. Size: " + imageBytes.length + " bytes");

    inputStream.close();
    byteArrayOutputStream.close();

    return  imageBytes;

  }

  private void isValidCountry() {
    String country = userCountry.getText();
    boolean IsValidCountry = UserValidation.isValidCountry(country);

    if (!IsValidCountry) {
      CountryLabel.setVisible(true);
      CountryLabel.setText("Invalid");
      CountryLabel.setStyle("-fx-text-fill: red;");
    } else {
      CountryLabel.setVisible(false);
    }
    updateSignUpButtonState();
  }

  private void isValidGender() {
    String gender = userGender.getText();
    boolean IsValidGender = UserValidation.isValidGender(gender);

    if (!IsValidGender) {
      GenderLabel.setVisible(true);
      GenderLabel.setText("Invalid");
      GenderLabel.setStyle("-fx-text-fill: red;");
    } else {
      GenderLabel.setVisible(false);
    }
    updateSignUpButtonState();
  }

  private void isValidConfirmPassword() {
    String confirmPassword = userConfirmPassword.getText();
    String password = userPassword.getText();
    boolean IsValidPassword = confirmPassword.equals(password);

    if (!IsValidPassword) {
      confirmPasswordLabel.setVisible(true);
      confirmPasswordLabel.setText("Invalid");
      confirmPasswordLabel.setStyle("-fx-text-fill: red;");
    } else {
      confirmPasswordLabel.setVisible(false);
    }
    updateSignUpButtonState();
  }

  private void isValidPassword() {
    String password = userPassword.getText();
    boolean IsValidPassword = UserValidation.isValidPassword(password);

    if (!IsValidPassword) {
      passwordLabel.setVisible(true);
      passwordLabel.setText("Invalid");
      passwordLabel.setStyle("-fx-text-fill: red;");
    } else {
      passwordLabel.setVisible(false);
    }
    updateSignUpButtonState();
  }

  private void isValidEmail() {
    String email = userEmail.getText();
    boolean isValidEmail = UserValidation.isVaildEmail(email);

    if (!isValidEmail) {
      UserEmailLabel.setVisible(true);
      UserEmailLabel.setText("Invalid");
      UserEmailLabel.setStyle("-fx-text-fill: red;");
    } else {
      UserEmailLabel.setVisible(false);
    }
    updateSignUpButtonState();
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
    updateSignUpButtonState();
  }

  private void isValidPhoneNumber()  {
    String phoneNumber = userPhoneNumber.getText();
    boolean isValidNumber = UserValidation.isValidPhoneNumber(phoneNumber);

      try {
          if(server.getUser(phoneNumber)!= null)
          {
            validUserLab.setVisible(true);
            validUserLab.setText("this user have already account");
            validUserLab.setStyle("-fx-text-fill: red;");

          }
          else{ validUserLab.setVisible(false);}
      } catch (RemoteException e) {
          throw new RuntimeException(e);
      }


          if (!isValidNumber) {
            UserNumberLabel.setVisible(true);
            UserNumberLabel.setText("Invalid");
            UserNumberLabel.setStyle("-fx-text-fill: red;");
          } else {
            UserNumberLabel.setVisible(false);
          }

      updateSignUpButtonState();
  }

  @FXML
  void addNewUser(ActionEvent event) throws IOException {
    if (signUpButton.isDisabled()) {
      validUserLab.setVisible(true);
      validUserLab.setText("can't add this user");
      validUserLab.setStyle("-fx-text-fill: red;");

    } else {
      validUserLab.setVisible(false);


    }
    try {
          User user = CreateUser();

        server = ServerConnection.getServer();

      //  System.out.println(server);

          server.signUp(user);
          user = server.getUser(user.getPhoneNumber());
          System.out.println(user);

    //   user.setUserId(1);

        CreateUserFile(user.getPhoneNumber(), user.getPassword(), user.getUserId());

      ClientStarter.setRoot("login");





      } catch (IOException e) {
          throw new RuntimeException(e);
      }



  }

  @FXML
  void goToSignin(ActionEvent event) {

      try {
          ClientStarter.setRoot("login");
      } catch (IOException e) {

      }
  }

  @FXML
  void addPhoto(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();
    File selectedFile = fileChooser.showOpenDialog(((Button) event.getSource()).getScene().getWindow());

    if (selectedFile != null) {
      Image img = new Image(selectedFile.toURI().toString());
      imageViewSignUp.setImage(img);
    }
  }
  private byte[] imageViewToBytes(ImageView imageView) throws IOException {
    Image image = imageView.getImage();
    PixelReader pixelReader = image.getPixelReader();

    WritableImage writableImage = new WritableImage(
            (int) image.getWidth(),
            (int) image.getHeight()
    );

    PixelWriter pixelWriter = writableImage.getPixelWriter();
    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        pixelWriter.setColor(x, y, pixelReader.getColor(x, y));
      }
    }

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    BufferedImage bufferedImage = new BufferedImage(
            (int) writableImage.getWidth(),
            (int) writableImage.getHeight(),
            BufferedImage.TYPE_INT_ARGB
    );

    for (int y = 0; y < writableImage.getHeight(); y++) {
      for (int x = 0; x < writableImage.getWidth(); x++) {
        javafx.scene.paint.Color fxColor = writableImage.getPixelReader().getColor(x, y);
        int argb = ((int) (fxColor.getOpacity() * 255) << 24) |
                ((int) (fxColor.getRed() * 255) << 16) |
                ((int) (fxColor.getGreen() * 255) << 8) |
                ((int) (fxColor.getBlue() * 255));
        bufferedImage.setRGB(x, y, argb);
      }
    }

    ImageIO.write(bufferedImage, "png", outputStream);
    return outputStream.toByteArray();
  }

  private  void CreateUserFile(String phoneNumber ,String password , int userId)
  {
    String PROPERTIES_FILE = "user_session.properties";
    // String PROPERTIES_FILE = "user_session.properties";

      try (FileOutputStream out = new FileOutputStream(PROPERTIES_FILE)) {
      Properties properties = new Properties();
      properties.setProperty("loggedInUser", String.valueOf(userId));
      properties.setProperty("phoneNumber", phoneNumber);
      properties.setProperty("password", password);

      properties.store(out, "User Session Data");


    } catch (IOException e) {
      e.printStackTrace();
    }

  }

}
