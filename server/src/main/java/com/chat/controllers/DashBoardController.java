package com.chat.controllers;

import com.chat.entity.User;
import com.chat.dao.impl.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DashBoardController implements Initializable {

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, Integer> idColumn;

    @FXML
    private TableColumn<User, String> emailColumn;

    @FXML
    private TableColumn<User, String> phoneColumn;

    @FXML
    private TableColumn<User, String> genderColumn;

    @FXML
    private TableColumn<User, String> countryColumn;

    @FXML
    private TableColumn<User, Integer> loginCountColumn;

    @FXML
    private TableColumn<User, Boolean> onlineColumn;

    // Disconnect column for the "Disconnect" button
    @FXML
    private TableColumn<User, Void> disconnectColumn;

    private final UserService userService = new UserService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (TableColumn<User, ?> column : userTable.getColumns()) {
            if (column == disconnectColumn) continue; // Skip the disconnect column
            @SuppressWarnings("unchecked")
            TableColumn<User, Object> col = (TableColumn<User, Object>) column;
            col.setCellFactory(new Callback<TableColumn<User, Object>, TableCell<User, Object>>() {
                @Override
                public TableCell<User, Object> call(TableColumn<User, Object> param) {
                    return new TableCell<User, Object>() {
                        private final Text text = new Text();

                        {
                            // Use only the graphic (our Text node) for display.
                            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                            setAlignment(Pos.CENTER);
                            text.setTextAlignment(TextAlignment.CENTER);
                            // Bind wrapping width to the column's width (minus padding)
                            text.wrappingWidthProperty().bind(param.widthProperty().subtract(10));
                            setGraphic(text);
                        }

                        @Override
                        protected void updateItem(Object item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty || item == null) {
                                text.setText(null);
                            } else {
                                text.setText(item.toString());
                            }
                        }
                    };
                }
            });
        }

        disconnectColumn.setCellFactory(new Callback<TableColumn<User, Void>, TableCell<User, Void>>() {
            @Override
            public TableCell<User, Void> call(TableColumn<User, Void> param) {
                return new TableCell<User, Void>() {
                    private final Button btn = new Button("Disconnect");

                    {
                        btn.getStyleClass().add("disconnect-button");
                        btn.setOnAction(event -> {
                            User currentUser = getTableView().getItems().get(getIndex());
                            disconnectUser(currentUser);
                        });
                        setAlignment(Pos.CENTER);
                        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        setGraphic(empty ? null : btn);
                    }
                };
            }
        });

        setupTable();
        loadUsers();
    }

    private void setupTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        loginCountColumn.setCellValueFactory(new PropertyValueFactory<>("countOfLogin"));
        onlineColumn.setCellValueFactory(new PropertyValueFactory<>("isOnline"));
        userTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void loadUsers() {
        List<User> users = userService.getAllUsers();
        ObservableList<User> observableUsers = FXCollections.observableArrayList(users);
        userTable.setItems(observableUsers);
    }

    // Dummy disconnect method – replace with your actual logic.
    private void disconnectUser(User user) {
        System.out.println("Disconnecting user: " + user.getUserId());
    }
}
