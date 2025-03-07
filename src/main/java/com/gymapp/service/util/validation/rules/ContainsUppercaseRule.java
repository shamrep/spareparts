package com.gymapp.service.util.validation.rules;

public class ContainsUppercaseRule implements ValidationRule<String> {

    @Override
    public boolean validate(String value) {
        return value.chars().anyMatch(Character::isUpperCase);
    }
}
