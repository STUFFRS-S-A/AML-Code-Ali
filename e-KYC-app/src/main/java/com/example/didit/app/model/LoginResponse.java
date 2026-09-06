package com.example.didit.app.model;

import lombok.Data;

@Data
public class LoginResponse {

    String loginToken;
    String refreshToken;

    boolean success;

    public LoginResponse(String loginToken, String refreshToken) {
        this.loginToken = loginToken;
        this.refreshToken = refreshToken;
        this.success = true;
    }
}