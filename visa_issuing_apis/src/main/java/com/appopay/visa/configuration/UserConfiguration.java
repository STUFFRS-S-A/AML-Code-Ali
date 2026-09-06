//package com.appopay.visa.configuration;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class UserConfiguration {
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable() // Disable CSRF protection for simplicity
//                .authorizeRequests()
//                .anyRequest().permitAll(); // Allow all requests without authentication
//        return http.build();
//    }
//}
