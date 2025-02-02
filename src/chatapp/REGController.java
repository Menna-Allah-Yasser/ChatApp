package chatapp;

import java.io.File;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class REGController {

    @FXML
    private TextField name;

    @FXML
    private Button choosePic;

    @FXML
    private Button yalla;

    @FXML
    private ImageView profileImg;
    
    private Stage stage;
    private Scene scene;
    private Parent chatRoot;

    @FXML
    public Image getProfileImg() {
        return profileImg.getImage();
    }

    @FXML
    public String getName() {
        return name.getText();
    }

    public void login(ActionEvent event) throws IOException{
        Image img = profileImg.getImage();
        String userName = name.getText();
        
        FXMLLoader chatLoader = new FXMLLoader(getClass().getResource("CHAT.fxml"));
        chatRoot = chatLoader.load();
        CHATController chatController = chatLoader.getController();
        chatController.setProfileImg(img);
        chatController.setUserName(userName);
        
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(chatRoot);
        stage.setScene(scene);
        stage.show();
    }

    public void uploadPic(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            Image c = new Image(file.toURI().toString());
            profileImg.setImage(c);
        } else {
            System.out.println("No file selected.");
        }
    }
}
