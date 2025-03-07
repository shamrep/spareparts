package com.gymapp.service.util.validation.rules;

public class ContainsSpecialCharacterRule implements ValidationRule<String> {

    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_+=<>?[]{}|,./";

    @Override
    public boolean validate(String value) {
        return value.chars().anyMatch(ch -> SPECIAL_CHARACTERS.indexOf(ch) >= 0);
    }
}
