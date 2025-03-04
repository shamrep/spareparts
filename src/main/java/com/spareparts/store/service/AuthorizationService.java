package com.spareparts.store.service;


import com.spareparts.store.service.model.RoleEnum;
import com.spareparts.store.service.util.TokenManager;
import io.jsonwebtoken.Claims;

import java.util.List;

public class AuthorizationService {


    public boolean isAuthorized(String token, List<RoleEnum> requredRoles) {

        Claims claims = TokenManager.decodeToken(token);
        List<RoleEnum> rolesFromToken = claims.get("roles", List.class);

        for(RoleEnum roleEnum : requredRoles) {
            if (rolesFromToken.contains(roleEnum)) {
                return true;
            }
        }

        return false;
    }

}
