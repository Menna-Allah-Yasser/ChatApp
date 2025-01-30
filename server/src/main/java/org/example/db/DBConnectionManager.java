package org.example.db;

import org.apache.commons.dbcp2.BasicDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnectionManager {
    private static BasicDataSource dataSource = new BasicDataSource();

    static {
        Properties props = new Properties();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("db.properties");
            props.load(fis);
            dataSource.setUrl(props.getProperty("MYSQL_DB_URL"));
            dataSource.setUsername(props.getProperty("MYSQL_DB_USERNAME"));
            dataSource.setPassword(props.getProperty("MYSQL_DB_PASSWORD"));
            dataSource.setMinIdle(5);
            dataSource.setMaxIdle(10);
            dataSource.setMaxTotal(20);
            dataSource.setMaxWaitMillis(5000);
            dataSource.setTestOnBorrow(true);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
