package com.example.didit.app.service;

import com.example.didit.app.entity.UserMasterEntity;
import com.example.didit.app.model.LoginRequest;
import com.example.didit.app.model.LoginResponse;
import com.example.didit.app.repository.UserMasterRepository;
import com.example.didit.app.security.AuthManager;
import com.example.didit.app.security.CustomUserDetails;
import com.example.didit.app.security.JwtUtil;
import com.example.didit.app.model.ApiResponse;
import com.example.didit.app.model.SignupRequest;
import com.example.didit.app.service.auth.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserMasterService {

    private final UserMasterRepository userMasterRepository;

    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthManager authManager;

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;


    public LoginResponse performLogin(LoginRequest loginRequest) {
//        UserMasterEntity userMaster = userMasterRepository.findByUserName(loginRequest.username())
//            .orElseThrow(() -> new CustomException("User not found"));
//
//        if (Boolean.TRUE.equals(userMaster.getAdUser())) {
//            throw new BusinessException(ResponseCodes.AdUser);
//        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(), loginRequest.password()));

        CustomUserDetails user = (CustomUserDetails) userDetailsService.loadUserByUsername(loginRequest.username());

        return new LoginResponse(jwtUtil.generateToken(user), null);
    }

    @Transactional
    public ApiResponse<String> performSignup(SignupRequest signupRequest) {
        if (userMasterRepository.findByUserName(signupRequest.username()).isPresent()) {
            return ApiResponse.fail("1001", "Username already exists");
        }

        UserMasterEntity userAccount = new UserMasterEntity();
        userAccount.setUserName(signupRequest.username());
        userAccount.setEmail(signupRequest.email());
        userAccount.setPassword(passwordEncoder.encode(signupRequest.password()));
        userAccount.setActive(true);
        userAccount.setRole("ROLE_USER");
        userAccount.setCreatedAt(Instant.now());

        userMasterRepository.save(userAccount);

        return ApiResponse.success("User registered successfully");
    }

}
