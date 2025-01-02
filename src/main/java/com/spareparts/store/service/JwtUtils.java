package com.spareparts.store.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

public class JwtUtils {
    // Replace this with your secure secret key
    private static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();

    // Token expiration time (e.g., 1 hour)
    private static final long EXPIRATION_TIME_MS = 3600_000;

    /**
     * Generate a JWT.
     *
     * @param claims Map of claims to include in the token.
     * @return Generated JWT as a String.
     */
    public static String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .claims(claims)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_MS))
                .signWith(SECRET_KEY).compact();
    }

    /**
     * Validate a JWT and return claims.
     *
     * @param token JWT token to validate.
     * @return Claims from the token if valid.
     * @throws JwtException if the token is invalid or expired.
     */
    public static Claims validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY) // Provide the secret key used for signing
                .build()
                .parseClaimsJws(token) // Validate the token
                .getBody(); // Extract claims
    }
}
