/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapp;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author ahmed
 */
public class CHATController {
    
    @FXML
    public ImageView profileImg; 
    @FXML
    private TextField message;
    
    @FXML
    private ListView list;
    @FXML
    private Label userName;
    
    
    public void setProfileImg(Image image) {
        profileImg.setImage(image);
    }
    
    public void setUserName(String s) {
    if (!s.isEmpty()) {
        userName.setText(s);
        
        } else {
            userName.setText("anonymous");
        }
        userName.setTextFill(Color.WHITE); 
        userName.setAlignment(Pos.CENTER);
    } 
    
    public void appendMessage(ActionEvent e) {
        String body = userName.getText() + ": " + message.getText();
        list.getItems().add(body);
        message.clear();
        
    }
}
