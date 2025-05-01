package com.example.unieats.model;

import javax.security.auth.callback.Callback;

public class BusinessUser {
    private String businessName;
    private String password;

    // Getters and setters
    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
