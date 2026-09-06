package com.example.didit.app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:3000/", "https://ahli.codovio.com", "https://ahli.codovio.com/") // Specify allowed origins
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                .allowedHeaders("Authorization", "Content-Type")
//                .allowCredentials(true); // Ensure credentials are allowed
//    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // Frontend domains
        config.setAllowedOrigins(List.of(
                "http://localhost:8080",
                "https://appokyc.appopay.com",
                "https://kyc.appopay.com",
                "https://ekyc.appopay.com",
                "http://localhost:5173",
                "http://localhost:8086"
        ));

        // REST methods
        config.setAllowedMethods(List.of(
                "GET", "POST", "PUT", "DELETE", "OPTIONS"
        ));

        // Headers sent by frontend
        config.setAllowedHeaders(List.of(
                "Authorization",
                "Content-Type",
                "idToken"
        ));

        // Headers you want frontend to read
        config.setExposedHeaders(List.of(
                "Authorization"
        ));
        // IMPORTANT: false for Bearer token APIs
        config.setAllowCredentials(false);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
