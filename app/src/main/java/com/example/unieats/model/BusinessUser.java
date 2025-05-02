package com.example.unieats.model;

import javax.security.auth.callback.Callback;

public class BusinessUser {
    private String businessName;
    private String email;
    private String password;

    public BusinessUser() {
        this.businessName = "";
        this.email = "";
        this.password = "";
    }

    public BusinessUser(String businessName, String email, String password) {
        this.businessName = businessName;
        this.email = email;
        this.password = password;
    }

    // Getters and setters
    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
