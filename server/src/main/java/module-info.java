module com.chat.server {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
  
    requires java.sql;
    requires java.rmi;
  
    requires commons.dbcp2;
    requires java.management;
  
    requires mysql.connector.j;
    requires javafx.graphics;
    exports com.chat to javafx.graphics;
    opens com.chat to javafx.fxml;
    opens com.chat.controllers to javafx.fxml;
    exports com.chat.controllers;
  
    exports com.chat.entity;
    exports com.chat.network;

    opens com.chat.network to java.rmi;
}

