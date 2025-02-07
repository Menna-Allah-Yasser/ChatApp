module com.chat.server {

    requires java.sql;
    requires java.rmi;

    requires commons.dbcp2;
    requires java.management;

    exports com.chat.entity;
    exports com.chat.network;
    opens com.chat.network to java.rmi;
}