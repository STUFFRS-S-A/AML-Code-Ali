//package com.example.didit.app.security;
//
//import com.campaign.management.repository.UserMasterRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.event.EventListener;
//import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.Instant;
//
//@Component
//@RequiredArgsConstructor
//public class AuthenticationSuccessListener {
//    private final UserMasterRepository userMasterRepository;
//
//    @EventListener
//    @Transactional
//    public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
//
//        String username = event.getAuthentication().getName();
//
//        userMasterRepository.findByUserName(username).ifPresent(user -> {
//            user.setLastSuccessLoginAt(Instant.now());
//            user.setFailedLoginAttempt(0);
//            userMasterRepository.save(user);
//        });
//    }
//}
