package com.chat.entity;

import java.io.Serializable;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;


public class User  implements Serializable {


    private Integer userId;
    private String phoneNumber;
    private String email;
    private byte[] picture;
    private String gender;
    private String country;
    private String bio;
    private LocalDate dob;
    private String password;
    private Integer countOfLogin;
    private String mode;
    private Boolean isChatbotEnabled;
    private String name;
    private String linkedinUrl;
    private String facebookUrl;
    private String twitterUrl;
    private boolean isOnline;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getCountOfLogin() {
        return countOfLogin;
    }

    public void setCountOfLogin(Integer countOfLogin) {
        this.countOfLogin = countOfLogin;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Boolean getIsChatbotEnabled() {
        return isChatbotEnabled;
    }

    public void setIsChatbotEnabled(Boolean isChatbotEnabled) {
        this.isChatbotEnabled = isChatbotEnabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLinkedinUrl() {
        return linkedinUrl;
    }

    public void setLinkedinUrl(String linkedinUrl) {
        this.linkedinUrl = linkedinUrl;
    }

    public String getFacebookUrl() {
        return facebookUrl;
    }

    public void setFacebookUrl(String facebookUrl) {
        this.facebookUrl = facebookUrl;
    }

    public String getTwitterUrl() {
        return twitterUrl;
    }

    public void setTwitterUrl(String twitterUrl) {
        this.twitterUrl = twitterUrl;
    }

    public boolean getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
               // ", picture=" + Arrays.toString(picture) +
                ", gender='" + gender + '\'' +
                ", country='" + country + '\'' +
                ", bio='" + bio + '\'' +
                ", dob=" + dob +
                ", password='" + password + '\'' +
                ", countOfLogin=" + countOfLogin +
                ", mode='" + mode + '\'' +
                ", isChatbotEnabled=" + isChatbotEnabled +
                ", name='" + name + '\'' +
                ", linkedinUrl='" + linkedinUrl + '\'' +
                ", facebookUrl='" + facebookUrl + '\'' +
                ", twitterUrl='" + twitterUrl + '\'' +
                ", isOnline=" + isOnline +
                '}';
    }
}