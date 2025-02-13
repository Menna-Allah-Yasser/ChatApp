package com.chat.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class SessionManager {
    private static final String PROPERTIES_FILE = "user_session.properties";
    private static int loggedInUser = -1;
    
    public static void setLoggedInUser(int userId, String phoneNumber, String password, String isloggedIn) {
        loggedInUser = userId;
        try (FileOutputStream out = new FileOutputStream(PROPERTIES_FILE)) {
            Properties properties = new Properties();
            properties.setProperty("loggedInUser", String.valueOf(userId));
            properties.setProperty("phoneNumber", phoneNumber);
            properties.setProperty("password", password);
            properties.setProperty("isloggedIn", isloggedIn);
            properties.store(out, "new user");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static int getLoggedInUser() {
        return loggedInUser;
    }
    
    public static void clearSession() {
        Properties properties = new Properties();
        try (FileInputStream in = new FileInputStream(PROPERTIES_FILE)) {
            properties.load(in);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        properties.remove("loggedInUser");
        properties.remove("password");
        properties.remove("isloggedIn");
        
        try (FileOutputStream out = new FileOutputStream(PROPERTIES_FILE)) {
            properties.store(out, "session cleared");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
