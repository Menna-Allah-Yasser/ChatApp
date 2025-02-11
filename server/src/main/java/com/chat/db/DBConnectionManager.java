package com.chat.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnectionManager {

    private static final class SingletonHolder {
        private static final DBConnectionManager SINGLETON = new DBConnectionManager();
    }

    public static DBConnectionManager getInstance() {
        return SingletonHolder.SINGLETON;
    }

    public static Connection getConnection() {
        String url = "jdbc:mysql://localhost/chatapp";
        String username = "root";
        String password = "12345678";

        try {
            Connection con = DriverManager.getConnection(url, username, password);
            //con.setAutoCommit(false);
            return con;
        } catch (SQLException e) {
            throw new RuntimeException("DB CONNECTION FAILED");
        }
    }
}
