package com.gymapp.service.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {

    private static final BCryptPasswordEncoder BCRYPT = new BCryptPasswordEncoder();

    public static String hashPassword(String password) {

        return BCRYPT.encode(password);
    }

    public static boolean verifyPassword(String hash, String plainPassword) {

        return BCRYPT.matches(plainPassword, hash);
    }
}
