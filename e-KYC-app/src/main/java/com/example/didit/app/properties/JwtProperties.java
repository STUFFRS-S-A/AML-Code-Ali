package com.example.didit.app.properties;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private Token access;
    private Token refresh;

    @Getter
    @Setter
    public static class Token {
        private String secret;
        private long expirationMs;
    }

    public SecretKey getAccessKey() {
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(this.getAccess().getSecret())
        );
    }
}
