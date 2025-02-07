module com.chat.client {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.rmi;
    //requires com.chat.server;
    requires java.sql;


    opens com.chat to javafx.fxml;
    exports com.chat;
    exports com.chat.controller;
    opens com.chat.controller to javafx.fxml;


}