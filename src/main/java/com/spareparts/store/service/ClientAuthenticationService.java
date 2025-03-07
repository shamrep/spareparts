package com.spareparts.store.service;

import com.spareparts.store.service.model.Client;
import com.spareparts.store.service.model.ClientRole;
import com.spareparts.store.service.util.PasswordUtil;
import com.spareparts.store.service.util.TokenManager;
import io.jsonwebtoken.Claims;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;

@RequiredArgsConstructor
public class ClientAuthenticationService {

    private final long expirationTime = 3600;
    @Getter
    private OffsetDateTime expirationDate;
    private final ClientService clientService;

    public ClientAuthenticationService() {
        this(new ClientService());
    }

    public String generateClientToken(Client client) {

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

    public List<ClientRole> getClientRoles(String token) {

        return extractTokenDetails(token).get("roles", List.class);

    }

    public boolean isValidToken(String token) {

        return TokenManager.validateToken(token);

    }

    public Optional<Client> authenticateClient(String email, String password) {

        Optional<Client> client = clientService.findClientByEmail(email);
//        String hashPassword = PasswordUtil.hashPassword(password);
        String hashPassword = password;

        return client.filter(c -> c.getPassword().equals(hashPassword));

    }

}
