package com.example.city_bus.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgatePass {
    @SerializedName("email")
    private String email;

    @SerializedName("newPassword")
    private String newPassword;

    @SerializedName("currentPassword")
    private String currentPassword;

    public ForgatePass(String email, String newPassword, String currentPassword) {
        this.email = email;
        this.newPassword = newPassword;
        this.currentPassword = currentPassword;
    }

    // Getter methods
    public String getEmail() {
        return email;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }
}
