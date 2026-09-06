package com.example.didit.app.controller;

import com.example.didit.app.model.ApiResponse;
import com.example.didit.app.model.LoginRequest;
import com.example.didit.app.model.LoginResponse;
import com.example.didit.app.model.SignupRequest;
import com.example.didit.app.service.UserMasterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {


    private final UserMasterService userMasterService;

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        return userMasterService.performLogin(loginRequest);
    }

    @PostMapping("/signup")
    public ApiResponse<String> signup(@Valid @RequestBody SignupRequest signupRequest) {
        return userMasterService.performSignup(signupRequest);
    }

}
