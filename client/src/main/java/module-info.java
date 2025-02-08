module com.chat.client {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.rmi;
    //requires com.chat.server;
    requires java.sql;
    requires javafx.graphics;
    requires java.desktop;
    //  exports com.chat to javafx.graphics;

    exports com.chat.network to java.rmi;


    opens com.chat to javafx.fxml;
    exports com.chat to javafx.graphics;
    exports com.chat.controller;
    opens com.chat.controller to javafx.fxml;


}