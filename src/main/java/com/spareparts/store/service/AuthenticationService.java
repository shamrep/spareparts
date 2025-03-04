package com.spareparts.store.service;

import com.spareparts.store.service.util.TokenManager;

public class AuthenticationService {

    public boolean isValidToken(String token) {

        return TokenManager.validateToken(token);

    }

}
