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
    @FXML
    private Label allCat;
    @FXML
    private Label familyCat;
    @FXML
    private Label friendCat;
    @FXML
    private Label workCat;
    @FXML
    private TextField search;
    @FXML
    private ListView<?> chats;
    @FXML
    private Label currentChatName;
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
    @FXML
    private ListView<ChatCardClient> chatsArea;

    private ServerRepository server = ServerConnection.getServer();

    public void renderMessages() {
        System.out.println("rendering message");
        Runnable r = () -> {

            try {
                List<Message> messages = server.getChatMessages(CurrentChat.chatId);
                Cordinator.getList().setAll(messages);
                System.out.println(messages);
            } catch (RemoteException e) {
                throw new RuntimeException("here");
            }
            Platform.runLater(() -> {
                list.setItems(Cordinator.getList());
                list.scrollTo(Cordinator.getList().size() );
            });


        };
        Cordinator.getScheduledExecutorService().execute(r);

        Runnable r2 = () -> {
            try {

                CurrentChat.participants = server.getChatParticipants(CurrentChat.chatId);
                CurrentChat.chat = server.getChatById(CurrentChat.chatId);
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


            Platform.runLater(() -> {
                if (CurrentChat.user == null) currentChatName.setText(CurrentChat.chat.getName());
                else currentChatName.setText(CurrentChat.user.getName());
            });


        };
        Cordinator.getScheduledExecutorService().execute(r2);


        //System.out.println(Cordinator.getList());

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


        System.out.println("contactList()");
        try {

            List<ChatCard> chatDTOs = server.getChatsForUser(SessionManager.getLoggedInUser());
            List<ChatCardClient> chatCards = chatDTOs.stream()
                    .map(dto -> new ChatCardClient(
                            dto.getChat_id(),
                            dto.getChat_name(),
                            dto.getMessage_id(),
                            dto.getMessage_desc(),
                            dto.getMessage_time(),
                            dto.getUser_Id(),
                            dto.getUser_name(),
                            dto.isUser_isOnline(),
                            dto.getUser_pictrue()
                    ))
                    .collect(Collectors.toList());

            Cordinator.getContactList().setAll(chatCards);
            chatsArea.setItems(Cordinator.getContactList());
            chatsArea.setCellFactory(lv -> new ListCell<ChatCardClient>() {
                private HBox content;
                private ImageView profileImageView;
                private Text username;
                private Text message;
                private Text timestamp;
                private Circle onlineIndicator;

                {

                    profileImageView = new ImageView();
                    profileImageView.setFitWidth(50);
                    profileImageView.setFitHeight(50);
                    profileImageView.setClip(new Circle(20, 20, 20));


                    username = new Text();
                    username.setFont(Font.font("Arial", 16));
                    username.setFill(Color.DARKBLUE);

                    message = new Text();
                    message.setFont(Font.font("Arial", 14));
                    message.setFill(Color.DIMGRAY);

                    timestamp = new Text();
                    timestamp.setFont(Font.font("Arial", 12));
                    timestamp.setFill(Color.GRAY);

                    onlineIndicator = new Circle(8);

                    VBox vBox = new VBox(username, message);
                    vBox.setSpacing(5);

                    VBox rightBox = new VBox(timestamp, onlineIndicator);
                    rightBox.setAlignment(Pos.CENTER_RIGHT);
                    rightBox.setSpacing(10);


                    content = new HBox(profileImageView, vBox, rightBox);
                    content.setSpacing(15);
                    content.setAlignment(Pos.CENTER_LEFT);
                    HBox.setHgrow(rightBox, Priority.ALWAYS);

                    content.setPadding(new Insets(10));

                    content.setStyle("-fx-background-color: #f4f4f8; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 2);");


                    content.setOnMouseEntered(e -> content.setStyle("-fx-background-color: #e0f7fa; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 8, 0, 0, 4);"));
                    content.setOnMouseExited(e -> content.setStyle("-fx-background-color: #f4f4f8; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 2);"));
                }

                @Override
                protected void updateItem(ChatCardClient chatItem, boolean empty) {
                    super.updateItem(chatItem, empty);
                    if (empty || chatItem == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        // ربط البيانات مباشرة بالخصائص القابلة للملاحظة
                        username.textProperty().bind(chatItem.chatNameProperty());
                        message.textProperty().bind(chatItem.messageDescProperty());

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                        timestamp.textProperty().bind(Bindings.createStringBinding(
                                () -> {
                                    LocalDateTime time = chatItem.messageTimeProperty().get();
                                    return time != null ? time.format(DateTimeFormatter.ofPattern("HH:mm")) : "";
                                },
                                chatItem.messageTimeProperty()
                        ));


                        onlineIndicator.fillProperty().bind(
                                Bindings.when(chatItem.userIsOnlineProperty())
                                        .then(Color.LIMEGREEN)
                                        .otherwise(Color.GRAY)
                        );

                        setGraphic(content);
                    }
                }

            });


        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }


    }


        /*public void renderMessages() {
            System.out.println("rendering message");
            ServerRepository server=ServerConnection.getServer();
            Runnable r=()->{

                try {
                    List<Message> messages=server.getChatMessages(CurrentChat.chatId);
                    Cordinator.getList().setAll(messages);
                    System.out.println(messages);
                } catch (RemoteException e) {
                    throw new RuntimeException("here");
                }
                Platform.runLater(()->{list.setItems(Cordinator.getList());});


            };
            Cordinator.getScheduledExecutorService().execute(r);

            Runnable r2=()-> {
                try {

                    CurrentChat.participants = server.getChatParticipants(CurrentChat.chatId);
                    //System.out.println(CurrentChat.chat.getName());
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
                            System.out.println("hi");
                            CurrentChat.user = server.getUser(CurrentChat.participants.get(1).getParticpantId());
                        } catch (RemoteException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
                else
                {
                    try {
                        CurrentChat.chat=server.getChatById(CurrentChat.chatId);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println(CurrentChat.chat);
                System.out.println(CurrentChat.chat.getName());

                if(CurrentChat.user==null)currentChatName.setText(CurrentChat.chat.getName());
                else currentChatName.setText(CurrentChat.chat.getName());
            };
            Cordinator.getScheduledExecutorService().execute(r2);


            //System.out.println(Cordinator.getList());

}
        }*/



}
















