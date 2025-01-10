package com.spareparts.store.service.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

public class TokenManager {
    // Replace this with your secure secret key
    private static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();

    // Token expiration time (e.g., 1 hour)
    private static final long EXPIRATION_TIME_MS = 3600_000;

    public static String generateToken(Map<String, Object> claims) {

        return Jwts.builder()
                .claims(claims)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_MS))
                .signWith(SECRET_KEY).compact();
    }

    //todo should return map
    public static Claims decodeToken(String token) {

        return Jwts
                .parser()
                .verifyWith(SECRET_KEY) // Provide the secret key used for signing
                .build()
                .parseSignedClaims(token) // Validate the token
                .getPayload(); // Extract claims
    }

    public static boolean validateToken(String token) {

        try {

            Jwts
                .parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token);

            return true;

        } catch (Exception e) {

            return false;

        }
    }

}
