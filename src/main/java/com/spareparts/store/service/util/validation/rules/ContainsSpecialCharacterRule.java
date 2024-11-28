package com.spareparts.store.service.util.validation.rules;

public class ContainsSpecialCharacterRule extends BaseRule<String> {
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_+=<>?[]{}|,./";

    public ContainsSpecialCharacterRule(String s) {
    }

    @Override
    public boolean validate(String value) {
        return value.chars().anyMatch(ch -> SPECIAL_CHARACTERS.indexOf(ch) >= 0);
    }
}
