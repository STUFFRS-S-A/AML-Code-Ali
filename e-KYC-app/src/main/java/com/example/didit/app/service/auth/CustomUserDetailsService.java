package com.example.didit.app.service.auth;

import com.example.didit.app.entity.UserMasterEntity;
import com.example.didit.app.repository.UserMasterRepository;
import com.example.didit.app.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserMasterRepository userMasterRepository;
    public UserDetails loadUserById(Long userId, Instant tokenIssuedAt) {
        UserMasterEntity user = userMasterRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return this.loadUserByEntity(user, tokenIssuedAt);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserMasterEntity user = userMasterRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return this.loadUserByEntity(user, Instant.now());
    }

    private UserDetails loadUserByEntity(UserMasterEntity user, Instant tokenIssuedAt) {


        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getRole() == null ? "ROLE_USER" : user.getRole()));

        return new CustomUserDetails(
                user.getId(),
                user.getUserName(),
                user.getPassword(),
                user.isActive(),
                tokenIssuedAt,
                authorities
        );
    }
}
