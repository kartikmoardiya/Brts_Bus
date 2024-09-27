package com.example.city_bus.Models;

public class LoginRequest {

    private String email;

    private String password;

    public LoginRequest(String email, String pass) {
        this.email = email;
        this.password = pass;
    }
}
