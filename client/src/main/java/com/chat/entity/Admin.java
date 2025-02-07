package com.chat.entity;

import java.io.Serializable;

public class Admin implements Serializable {

    private int adminId;
    private String password;
    private String email;

    public Admin() {}

    public Admin(String password, String email) {
        this.password = password;
        this.email = email;
    }

    public Admin(int adminId, String password, String email) {
        this.adminId = adminId;
        this.password = password;
        this.email = email;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return getEmail();
    }
}
