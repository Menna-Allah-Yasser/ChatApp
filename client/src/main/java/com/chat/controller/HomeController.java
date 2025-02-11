package com.chat.controller;

import com.chat.entity.ChatCard;
import com.chat.entity.ChatCardClient;
import com.chat.entity.Message;
import com.chat.network.ServerConnection;
import com.chat.network.ServerRepository;
import com.chat.utils.Cordinator;
import com.chat.utils.CurrentChat;
import com.chat.utils.SessionManager;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class HomeController {
    @FXML private Label allCat;
    @FXML private Label familyCat;
    @FXML private Label friendCat;
    @FXML private Label workCat;
    @FXML private TextField search;
    @FXML private ListView<?> chats;
    @FXML private Label currentChatName;
    @FXML private Label fav;
    @FXML private BorderPane mainPane;
    @FXML private Label family;
    @FXML private ListView<Object> list;
    @FXML private TextField message;
    @FXML private ListView<ChatCardClient> chatsArea;

    private ServerRepository server = ServerConnection.getServer();

    public void renderMessages() {
        System.out.println("Rendering messages");
        Runnable r = () -> {
            try {
                List<Message> messages = server.getChatMessages(CurrentChat.chatId);
                Cordinator.getList().setAll(messages);
                System.out.println(messages);
            } catch (RemoteException e) {
                throw new RuntimeException("Error fetching messages", e);
            }
            Platform.runLater(() -> {
                list.setItems(Cordinator.getList());
                list.scrollTo(Cordinator.getList().size());
            });
        };
        Cordinator.getScheduledExecutorService().execute(r);
    }

    @FXML
    public void onSendClick() {
        if (!message.getText().trim().isEmpty()) {
            Message nMessage = new Message(message.getText(), LocalDateTime.now(), CurrentChat.chatId, SessionManager.getLoggedInUser());
            Cordinator.getList().add(nMessage);
            list.scrollTo(Cordinator.getList().size() - 1);
            message.setText("");
            Runnable r = () -> {
                try {
                    server.sendMessage(nMessage);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            };
            Cordinator.getScheduledExecutorService().execute(r);
        }
    }

    @FXML
    public void initialize() {
        renderContactList();
        if (CurrentChat.chatId > 0) {
            renderMessages();
        }
        list.setCellFactory(param -> new ListCell<Object>() {
            private final VBox vbox = new VBox();
            private final Label senderLabel = new Label();
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
                textFlow.setMaxWidth(400);
                textFlow.setMinHeight(Region.USE_PREF_SIZE);
                vbox.getChildren().addAll(senderLabel, textFlow, timestampLabel);
            }

            @Override
            protected void updateItem(Object message, boolean empty) {
                super.updateItem(message, empty);
                if (empty || message == null) {
                    setGraphic(null);
                } else {
                    Message messageObj = (Message) message;
                    messageLabel.setText(messageObj.getDescription());
                    timestampLabel.setText(messageObj.getTime().toString());
                    if (messageObj.getUser_id() == SessionManager.getLoggedInUser()) {
                        senderLabel.setText("You");
                        textFlow.setStyle("-fx-background-color: #73137B; -fx-background-radius: 15px; -fx-padding: 5px;");
                        messageLabel.setStyle("-fx-text-fill: white;");
                        vbox.setAlignment(Pos.CENTER_RIGHT);
                    } else {
                        try {
                            senderLabel.setText(server.getUser(messageObj.getUser_id()).getName());
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

    private void renderContactList() {
        System.out.println("Rendering contact list");
        try {
            List<ChatCard> chatDTOs = server.getChatsForUser(1);
            List<ChatCardClient> chatCards = chatDTOs.stream()
                    .map(dto -> new ChatCardClient(
                            dto.getChat_id(), dto.getChat_name(), dto.getMessage_id(),
                            dto.getMessage_desc(), dto.getMessage_time(), dto.getUser_Id(),
                            dto.getUser_name(), dto.isUser_isOnline(), dto.getUser_pictrue()
                    ))
                    .collect(Collectors.toList());

            Cordinator.getContactList().setAll(chatCards);
            chatsArea.setItems(Cordinator.getContactList());
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
    }
}