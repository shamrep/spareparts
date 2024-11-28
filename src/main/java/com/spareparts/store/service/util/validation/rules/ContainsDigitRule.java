package com.spareparts.store.service.util.validation.rules;

public class ContainsDigitRule extends BaseRule<String> {
    @Override
    public boolean validate(String value) {
        return value.chars().anyMatch(Character::isDigit);
    }
}
