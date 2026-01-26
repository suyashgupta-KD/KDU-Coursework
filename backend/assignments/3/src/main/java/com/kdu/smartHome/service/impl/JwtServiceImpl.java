package com.kdu.smartHome.service.impl;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Creates and parses JWT tokens.
 */

@Service
public class JwtServiceImpl {
    private final SecretKey key;
    private final long expMillis;

    public JwtServiceImpl(@Value("${security.jwt.secret}") String secret,
            @Value("${security.jwt.expMinutes}") long expMinutes) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expMillis = expMinutes * 60_000L;
    }

    public String generateToken(Long userId) {
        long now = System.currentTimeMillis();

        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("user_id", userId)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + expMillis))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Jws<Claims> parse(String token) throws JwtException {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }
}
