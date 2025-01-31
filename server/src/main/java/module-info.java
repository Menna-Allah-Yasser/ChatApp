module org.chat.server {

    requires java.sql;
    requires java.rmi;
    //requires com.chat.client.controller;


    requires com.chat.sharedResources;
    requires commons.dbcp2;
    requires java.management;
}