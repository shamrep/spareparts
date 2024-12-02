package com.spareparts.store.service.util.validation.rules;

public class ContainsLowercaseRule implements ValidationRule<String> {

    @Override
    public boolean validate(String value) {
        return value.chars().anyMatch(Character::isLowerCase);
    }
}
