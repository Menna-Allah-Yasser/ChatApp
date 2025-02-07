package com.chat.dao.impl;

import com.chat.dao.repository.UserRepository;
import com.chat.db.DBConnectionManager;
import com.chat.entity.User;

import java.sql.*;
import java.util.ArrayList;

public class UserService  implements UserRepository {

    private Connection connection;

    public UserService() {
        connection = DBConnectionManager.getConnection();

    }

    @Override
    public User findUserByPhoneNumber(String phoneNumber) {
        String query = "SELECT * FROM user WHERE phone_number = ?";
        User userDTO = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, phoneNumber);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
             /*    if (!resultSet.next()) {
                    return null;
                }

                userDTO = new UserDTO();
                userDTO.setUserId(resultSet.getInt("user_id"));
                userDTO.setPhoneNumber(resultSet.getString("phone_number"));
                userDTO.setEmail(resultSet.getString("email"));
                userDTO.setPicture(resultSet.getBytes("picture"));
                userDTO.setGender(resultSet.getString("gender"));
                userDTO.setCountry(resultSet.getString("country"));
                userDTO.setBio(resultSet.getString("bio"));
                userDTO.setDob(resultSet.getDate("DOB"));
                userDTO.setPassword(resultSet.getString("password"));
                userDTO.setCountOfLogin(resultSet.getInt("count_of_login"));
                userDTO.setMode(resultSet.getString("mode"));
                userDTO.setIsChatbotEnabled(resultSet.getBoolean("is_chatbot_enabled"));
                userDTO.setName(resultSet.getString("Username"));
                userDTO.setLinkedinUrl(resultSet.getString("linkedin_url"));
                userDTO.setFacebookUrl(resultSet.getString("facebook_url"));
                userDTO.setTwitterUrl(resultSet.getString("twitter_url"));
                userDTO.setIsOnline(resultSet.getBoolean("is_online")); */

                userDTO = createDtoObject(resultSet);
            }
        } catch (SQLException e) {

            throw new RuntimeException("Error finding user by phone number", e);
        }






        return userDTO ;
    }

    @Override
    public void deleteUserByPhoneNumber(String phoneNumber) {
        String query = "DELETE FROM user WHERE phone_number = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, phoneNumber);
            int rowsAffected = preparedStatement.executeUpdate();
            connection.commit();
            if (rowsAffected == 0) {
                System.out.println("No user found with the phone number: " + phoneNumber);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting user by phone number", e);
        }
    }

    @Override
    public void updateUser(User userDTO) {
        if (connection == null) {
            System.out.println("Database connection is not initialized!");
            return;
        }
        if (userDTO == null) {
            System.out.println("UserDTO cannot be null");
            return;
        }
        if (userDTO.getUserId() == null) {
            System.out.println("User ID cannot be null");
            return ;
        }

        //User user = findUserById(userDTO.getUserId());


        String query = "UPDATE user SET " +
                "phone_number = ?, " +
                "email = ?, " +
                "picture = ?, " +
                "gender = ?, " +
                "country = ?, " +
                "bio = ?, " +
                "DOB = ?, " +
                "password = ?, " +
                "count_of_login = ?, " +
                "mode = ?, " +
                "is_chatbot_enabled = ?, " +
                "name = ?, " +
                "linkedin_url = ?, " +
                "facebook_url = ?, " +
                "twitter_url = ?, " +
                "is_online = ? " +
                "WHERE user_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userDTO.getPhoneNumber());
            preparedStatement.setString(2, userDTO.getEmail());
            preparedStatement.setBytes(3, userDTO.getPicture());
            preparedStatement.setString(4, userDTO.getGender());
            preparedStatement.setString(5, userDTO.getCountry());
            preparedStatement.setString(6, userDTO.getBio());

            if (userDTO.getDob() != null) {
                preparedStatement.setDate(7, new java.sql.Date(userDTO.getDob().getTime()));
            } else {
                preparedStatement.setNull(7, Types.DATE);
            }

            preparedStatement.setString(8, userDTO.getPassword());
            preparedStatement.setObject(9, userDTO.getCountOfLogin());
            preparedStatement.setString(10, userDTO.getMode());
            preparedStatement.setBoolean(11, userDTO.getIsChatbotEnabled());
            preparedStatement.setString(12, userDTO.getName());
            preparedStatement.setString(13, userDTO.getLinkedinUrl());
            preparedStatement.setString(14, userDTO.getFacebookUrl());
            preparedStatement.setString(15, userDTO.getTwitterUrl());
            preparedStatement.setBoolean(16, userDTO.getIsOnline());
            preparedStatement.setInt(17, userDTO.getUserId());

            int rowsUpdated = preparedStatement.executeUpdate();
            connection.commit();
            if (rowsUpdated > 0) {
                System.out.println("User updated successfully!");
            } else {
                System.out.println("No user found with the given ID.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addNewUser(User userDTO) {
        String query = "INSERT INTO user (" +
                "phone_number, email, picture, gender, country, bio, dob, password, count_of_login, mode, " +
                "is_chatbot_enabled, name, linkedin_url, facebook_url, twitter_url, is_online) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userDTO.getPhoneNumber());
            preparedStatement.setString(2, userDTO.getEmail());
            preparedStatement.setBytes(3, userDTO.getPicture());
            preparedStatement.setString(4, userDTO.getGender());
            preparedStatement.setString(5, userDTO.getCountry());
            preparedStatement.setString(6, userDTO.getBio());
            preparedStatement.setDate(7, new java.sql.Date(userDTO.getDob().getTime()));
            preparedStatement.setString(8, userDTO.getPassword());
            preparedStatement.setObject(9, userDTO.getCountOfLogin());
            preparedStatement.setString(10, userDTO.getMode());
            preparedStatement.setBoolean(11, userDTO.getIsChatbotEnabled());
            preparedStatement.setString(12, userDTO.getName());
            preparedStatement.setString(13, userDTO.getLinkedinUrl());
            preparedStatement.setString(14, userDTO.getFacebookUrl());
            preparedStatement.setString(15, userDTO.getTwitterUrl());
            preparedStatement.setBoolean(16, userDTO.getIsOnline());

            int rowsInserted = preparedStatement.executeUpdate();
            connection.commit();
            if (rowsInserted > 0) {
                System.out.println("New user added successfully!");
            } else {
                System.out.println("Failed to add new user.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error adding new user", e);
        }
    }

    @Override
    public boolean authenticateUser(String phoneNumber, String password) {
        String query = "SELECT password FROM user WHERE phone_number = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, phoneNumber);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next()) {
                    return false;
                }

                String pass = resultSet.getString("password");
                return pass.equals(password);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error authenticating user", e);
        }
    }

    private void updateUserField(String fieldName, Object value, int userId) {
        String query = "UPDATE user SET " + fieldName + " = ? WHERE user_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, value);
            preparedStatement.setInt(2, userId);

            int rowsUpdated = preparedStatement.executeUpdate();
            connection.commit();
            if (rowsUpdated == 0) {
                System.out.println("No user found with ID " + userId);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating user field", e);
        }
    }

    @Override
    public void updateStatus(int userId, String status) {
        updateUserField("status", status, userId);
    }

    @Override
    public void updateChatbotEnabled(int userId, Boolean status) {
        updateUserField("is_chatbot_enabled", status, userId);
    }

    @Override
    public void updateOnline(int userId, Boolean isOnline) {
        updateUserField("is_online", isOnline, userId);
    }

    @Override
    public void updateUserImage(int userID, byte[] img) {
        updateUserField("picture", img, userID);
    }

    public User createDtoObject(ResultSet resultSet)
    {


        User userDTO = null;



        try {
            while (resultSet.next()) {

                userDTO = new User();
                userDTO.setUserId(resultSet.getInt("user_id"));
                userDTO.setPhoneNumber(resultSet.getString("phone_number"));
                userDTO.setEmail(resultSet.getString("email"));
                userDTO.setPicture(resultSet.getBytes("picture"));
                userDTO.setGender(resultSet.getString("gender"));
                userDTO.setCountry(resultSet.getString("country"));
                userDTO.setBio(resultSet.getString("bio"));
                userDTO.setDob(resultSet.getDate("DOB"));
                userDTO.setPassword(resultSet.getString("password"));
                userDTO.setCountOfLogin(resultSet.getInt("count_of_login"));
                userDTO.setMode(resultSet.getString("mode"));
                userDTO.setIsChatbotEnabled(resultSet.getBoolean("is_chatbot_enabled"));
                userDTO.setName(resultSet.getString("name"));
                userDTO.setLinkedinUrl(resultSet.getString("linkedin_url"));
                userDTO.setFacebookUrl(resultSet.getString("facebook_url"));
                userDTO.setTwitterUrl(resultSet.getString("twitter_url"));
                userDTO.setIsOnline(resultSet.getBoolean("is_online"));






            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return userDTO ;
    }

    @Override
    public User findUserById(int userId) {

        String query = "SELECT * FROM user WHERE user_id = ?";
        User userDTO = null;


        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
             /*    if (!resultSet.next()) {
                    return null;
                }

                userDTO = new UserDTO();
                userDTO.setUserId(resultSet.getInt("user_id"));
                userDTO.setPhoneNumber(resultSet.getString("phone_number"));
                userDTO.setEmail(resultSet.getString("email"));
                userDTO.setPicture(resultSet.getBytes("picture"));
                userDTO.setGender(resultSet.getString("gender"));
                userDTO.setCountry(resultSet.getString("country"));
                userDTO.setBio(resultSet.getString("bio"));
                userDTO.setDob(resultSet.getDate("DOB"));
                userDTO.setPassword(resultSet.getString("password"));
                userDTO.setCountOfLogin(resultSet.getInt("count_of_login"));
                userDTO.setMode(resultSet.getString("mode"));
                userDTO.setIsChatbotEnabled(resultSet.getBoolean("is_chatbot_enabled"));
                userDTO.setName(resultSet.getString("Username"));
                userDTO.setLinkedinUrl(resultSet.getString("linkedin_url"));
                userDTO.setFacebookUrl(resultSet.getString("facebook_url"));
                userDTO.setTwitterUrl(resultSet.getString("twitter_url"));
                userDTO.setIsOnline(resultSet.getBoolean("is_online")); */

                userDTO = createDtoObject(resultSet);
            }
        } catch (SQLException e) {

            throw new RuntimeException("Error finding user by phone number", e);
        }






        return  userDTO ;





    }

    @Override
    public ArrayList<User> getAllUsers(ArrayList<Integer> usersId) {

        ArrayList<User>arrayList = new ArrayList<>();

        for (Integer userId : usersId) {

            arrayList.add(findUserById(userId));

        }
        return arrayList;
    }

    public static void main(String[] args) {
        UserService service = new UserService();
        User user =service.findUserById(1);
        System.out.println(user);
        user.setEmail("menna@gmail.com");
        service.updateUser(user);
    }

}


