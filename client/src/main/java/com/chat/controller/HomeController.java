package com.chat.controller;

import com.chat.entity.Message;
import com.chat.network.ServerConnection;
import com.chat.network.ServerRepository;
import com.chat.utils.Cordinator;
import com.chat.utils.CurrentChat;
import com.chat.utils.SessionManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;

import javax.swing.text.html.ImageView;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HomeController {
    @FXML
    private Label all;

    @FXML
    private TextField search;

    @FXML
    private Label work;

    @FXML
    private Label currentChatName;

    @FXML
    private ListView<?> chats;



    @FXML
    private Label fav;

    @FXML
    private BorderPane mainPane;

    @FXML
    private Label family;

    @FXML
    private ListView<Object> list;

    @FXML
    private TextField message;


    ServerRepository server=ServerConnection.getServer();
    @FXML
    public void onSendClick()
    {

        if(message.getText().equals(""));
        else
        {
            Message nMessage=new Message(message.getText(), LocalDateTime.now(),CurrentChat.chatId,SessionManager.getLoggedInUser());
            Cordinator.getList().add(nMessage);
            list.scrollTo(Cordinator.getList().size()-1);
            message.setText("");
            Runnable r=()->{try {
                server.sendMessage(nMessage);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }};
            Cordinator.getScheduledExecutorService().execute(r);

        }

    }
    @FXML
    public void initialize() {

        if(CurrentChat.chatId>0)
        {
            renderMessages();
        }
        list.setCellFactory(param -> new ListCell<Object>() {
            private final VBox vbox = new VBox();      // Vertical layout container
            private final Label senderLabel = new Label();  // Sender name
            private final TextFlow textFlow = new TextFlow();
            private final Label messageLabel = new Label();
            private final Label timestampLabel = new Label();

            {
                senderLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12px; -fx-text-fill: black;");
                messageLabel.setMaxWidth(400);
                messageLabel.setWrapText(true);
                messageLabel.setPadding(new Insets(5, 10, 5, 10));
                timestampLabel.setStyle("-fx-font-size: 10px; -fx-text-fill: gray;");
                textFlow.getChildren().add(messageLabel);
                textFlow.setMaxWidth(400);  // Ensure text bubbles don't exceed max width
                textFlow.setMinHeight(Region.USE_PREF_SIZE);
                vbox.getChildren().addAll(senderLabel, textFlow, timestampLabel);
            }

            @Override
            protected void updateItem(Object message, boolean empty) {
                super.updateItem(message, empty);

                if (empty || message == null) {
                    setGraphic(null);
                } else {
                    Message message1=(Message)message;

                    messageLabel.setText(message1.getDescription());
                    timestampLabel.setText(message1.getTime().toString());

                    if (message1.getUser_id()==SessionManager.getLoggedInUser()) {
                        senderLabel.setText("You");
                        textFlow.setStyle("-fx-background-color: #73137B; -fx-background-radius: 15px; -fx-padding: 5px;");
                        messageLabel.setStyle("-fx-text-fill: white;");
                        vbox.setAlignment(Pos.CENTER_RIGHT);
                    } else {

                        try {
                            senderLabel.setText(server.getUser(message1.getUser_id()).getName());
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                        textFlow.setStyle("-fx-background-color: #E5E5EA; -fx-background-radius: 15px; -fx-padding: 5px;");
                        messageLabel.setStyle("-fx-text-fill: black;");
                        vbox.setAlignment(Pos.CENTER_LEFT);
                    }

                    setGraphic(vbox);
                }
            }
        });


    }
    public void renderMessages()
    {
        System.out.println("rendering message");
      Runnable r=()->{

            try {
                List<Message> messages=server.getChatMessages(CurrentChat.chatId);
                Cordinator.getList().setAll(messages);
                System.out.println(messages);
            } catch (RemoteException e) {
                throw new RuntimeException("here");
            }
            Platform.runLater(()->{list.setItems(Cordinator.getList());list.scrollTo(Cordinator.getList().size()-1);});


       };
  Cordinator.getScheduledExecutorService().execute(r);

Runnable r2=()-> {
    try {

        CurrentChat.participants = server.getChatParticipants(CurrentChat.chatId);
        CurrentChat.chat=server.getChatById(CurrentChat.chatId);
    } catch (RemoteException e) {
        System.out.println(e.getMessage());

    }

    if (CurrentChat.participants.size() == 2) {
        System.out.println("hi");
        if (CurrentChat.participants.get(0).getParticpantId() != SessionManager.getLoggedInUser()) {
            try {

                CurrentChat.user = server.getUser(CurrentChat.participants.get(0).getParticpantId());

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            try {

                CurrentChat.user = server.getUser(CurrentChat.participants.get(1).getParticpantId());
            } catch (RemoteException e) {
                System.out.println(e.getMessage());
            }
        }
    }


    Platform.runLater(()->{
        if(CurrentChat.user==null)currentChatName.setText(CurrentChat.chat.getName());
        else currentChatName.setText(CurrentChat.user.getName());});


};
Cordinator.getScheduledExecutorService().execute(r2);




 //System.out.println(Cordinator.getList());

   }




}