package com.spareparts.store.service;

import com.spareparts.store.service.model.Client;
import com.spareparts.store.service.model.ClientCredentials;
import com.spareparts.store.service.util.JwtUtils;

import java.io.Reader;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

public class ClientAuthorizationService {

    private OffsetDateTime expirationDate;

    public String generateToken(Client client) {

        Map<String, Object> claims = new HashMap<>();
        expirationDate = OffsetDateTime.now();

        claims.put("email", client.getEmail());
        claims.put("expiresIn", expirationDate);
        claims.put("userId", client.getId());

        return JwtUtils.generateToken(claims);
    }

    public boolean validateToken(String token) {
//
        return true;
    }

    public String getToken(Reader reader)
    {
        return null;
    }

    public OffsetDateTime getExpirationDate() {
        return expirationDate;
    }


}
