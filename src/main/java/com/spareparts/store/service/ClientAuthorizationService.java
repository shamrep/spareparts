package com.spareparts.store.service;

import com.spareparts.store.service.model.Client;
import com.spareparts.store.service.util.TokenManager;
import io.jsonwebtoken.Claims;
import lombok.Getter;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

public class ClientAuthorizationService {

    private final long expirationTime = 3600;
    @Getter
    private OffsetDateTime expirationDate;

    public String authenticateClient(Client client) {

        Map<String, Object> claims = new HashMap<>();
        expirationDate = OffsetDateTime.now().plusHours(24);

        claims.put("email", client.getEmail());
        claims.put("exp", expirationDate.toEpochSecond());
        claims.put("userId", client.getId());
        claims.put("roles", client.getRoles());

        return TokenManager.generateToken(claims);

    }

    public boolean isTokenValid(String token) {

        if (TokenManager.validateToken(token)) {

            Claims claims = TokenManager.decodeToken(token);

            Long expTimestamp = claims.get("exp", Long.class);

            if (OffsetDateTime.ofInstant(Instant.ofEpochSecond(expTimestamp), ZoneOffset.UTC).isBefore(OffsetDateTime.now())) {

                throw new RuntimeException("Token is expired!");

            }

        }

        return false;

    }

    public Claims extractTokenDetails(String token) {

        return TokenManager.decodeToken(token);

    }

}
