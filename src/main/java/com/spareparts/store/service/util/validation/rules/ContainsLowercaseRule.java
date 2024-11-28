package com.spareparts.store.service.util.validation.rules;

public class ContainsLowercaseRule extends BaseRule<String> {

    public ContainsLowercaseRule(String message) {
        super(message);
    }

    @Override
    public boolean validate(String value) {
        return value.chars().anyMatch(Character::isLowerCase);
    }
}
