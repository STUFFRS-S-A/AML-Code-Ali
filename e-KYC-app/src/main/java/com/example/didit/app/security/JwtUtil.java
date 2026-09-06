package com.example.didit.app.security;

import com.example.didit.app.properties.JwtProperties;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {
//    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//    private static final long LOGIN_TOKEN_EXPIRATION = 1000 * 60 * 5 * 50; // 5 minutes for login token * 10 = 50 mins
//    private static final long REFRESH_TOKEN_EXPIRATION = 1000 * 60 * 10; // 10 minutes for refresh token

    private final JwtProperties jwtProperties;

    public String generateToken(CustomUserDetails user) {

        return Jwts.builder()
                .setSubject(String.valueOf(user.getUserId()))
                .claim("authorities",
                        user.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .toList())
                .claim("userName", user.getUsername())
                .claim("userId", user.getUserId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getAccess().getExpirationMs()))
                .signWith(jwtProperties.getAccessKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Long extractUserId(String token) {
        return Long.valueOf(getClaims(token).getSubject());
    }
    public Instant getIssuedAt(String token) {
        Claims claims = getClaims(token);
        Date issuedAt = claims.getIssuedAt();
        return issuedAt != null ? issuedAt.toInstant() : null;
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtProperties.getAccessKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


}
