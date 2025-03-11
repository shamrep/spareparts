package com.gymapp.service.model;

public enum ClientRole {

    ADMIN,
    USER,
    TRAINER,
    OWNER,
    BOSS;

    public static ClientRole fromString(String roleName) {
        return ClientRole.valueOf(roleName.toUpperCase());
    }

}
