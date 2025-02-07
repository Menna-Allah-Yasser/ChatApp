package com.chat.client.utils;


 import java.util.Properties;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

    public class SessionManager {
        private static final String PROPERTIES_FILE = "user_session.properties";
        private static int  loggedInUser = -1;
        public static void setLoggedInUser(int  userId, String phoneNumber,String password) {
            loggedInUser = userId;
            try (FileOutputStream out = new FileOutputStream(PROPERTIES_FILE)) {
                Properties properties = new Properties();
                properties.setProperty("loggedInUser", String.valueOf(userId));
                properties.setProperty("phoneNumber", password);
                properties.setProperty("password", password);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public static int  getLoggedInUser() {
            return loggedInUser;
        }
        public static void clearSession() {


            try (FileOutputStream out = new FileOutputStream(PROPERTIES_FILE)) {
                Properties properties = new Properties();
                properties.remove("loggedInUser");
                properties.remove("password");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
