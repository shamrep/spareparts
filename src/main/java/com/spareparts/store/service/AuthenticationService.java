package com.spareparts.store.service;

import com.spareparts.store.service.util.TokenManager;

public class AuthenticationService {

    private final TokenManager tokenManager;

    public AuthenticationService(TokenManager tokenManager) {

        this.tokenManager = tokenManager;

    }

    public boolean isValidToken(String token) {

        return TokenManager.validateToken(token);

    }

}
