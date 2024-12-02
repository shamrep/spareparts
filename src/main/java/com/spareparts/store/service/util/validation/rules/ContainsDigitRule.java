package com.spareparts.store.service.util.validation.rules;

public class ContainsDigitRule implements ValidationRule<String> {

    @Override
    public boolean validate(String value) {
        return value.chars().anyMatch(Character::isDigit);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
