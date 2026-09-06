//package com.example.didit.app.security;
//
//import com.campaign.management.repository.UserMasterRepository;
//import com.campaign.management.util.AppConstants;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.event.EventListener;
//import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
//import org.springframework.stereotype.Component;
//
//import java.time.Instant;
//
//@Component
//@RequiredArgsConstructor
//public class AuthenticationFailureListener {
//    private final UserMasterRepository userMasterRepository;
//
//    @Value("${auth.login.max-failed-login:5}")
//    private int maxFailedAttempts;
//
//    @Transactional
//    @EventListener
//    public void onFailure(AbstractAuthenticationFailureEvent event) {
//
//        String username = event.getAuthentication().getName();
//
//        userMasterRepository.findByUserName(username).ifPresent(user -> {
//            if(user.getId()== AppConstants.SuperAdminUserId)
//                return;
//
//            int failedLoginAttempt = user.getFailedLoginAttempt() + 1;
//            user.setFailedLoginAttempt(failedLoginAttempt);
//            user.setLastLoginAttemptAt(Instant.now());
//            if(failedLoginAttempt >= maxFailedAttempts && !user.isLocked()) {
//                user.setLocked(true);
//                user.setLockedAt(Instant.now());
//            }
//            userMasterRepository.save(user);
//        });
//    }
//}
