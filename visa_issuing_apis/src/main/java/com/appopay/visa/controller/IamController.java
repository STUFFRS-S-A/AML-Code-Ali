package com.appopay.visa.controller;

import com.appopay.visa.model.CustomerDTO;
import com.appopay.visa.model.IamDTO;
import com.appopay.visa.service.IamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class IamController {
//    @Autowired
//    private AuthenticationManager authenticationManager;
    @Autowired
    private IamService iamService;

    private static final Logger log = LoggerFactory.getLogger(CardManagementController.class);

    @PostMapping("/login")
    public String login(@RequestBody IamDTO loginRequest) {
        log.info("calling service for login");
        return iamService.login(loginRequest);
    }

    @PostMapping("/signup")
    public String signup(@RequestBody IamDTO signupRequest) {
        log.info("calling service for signup register");
        return iamService.signUpRegister(signupRequest);
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<CustomerDTO> getProfile(@PathVariable Long id) {
        log.info("calling service for get profile");
        return iamService.getProfile(id);
    }
}
