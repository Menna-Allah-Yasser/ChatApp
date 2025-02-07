module com.chat.client {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.rmi;

    opens com.chat.client to javafx.fxml;
    exports com.chat.client;
    exports com.chat.client.controller;
    opens com.chat.client.controller to javafx.fxml;
}