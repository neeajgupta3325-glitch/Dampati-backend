package org.dampati.backend.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service

public class JwtService {
    private final SecretKey secretKey = Keys.hmacShaKeyFor(
            "dampati-secret-key-for-jwt-authentication-2026".getBytes()
    )    ;
    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .signWith(secretKey)
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
    public boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
