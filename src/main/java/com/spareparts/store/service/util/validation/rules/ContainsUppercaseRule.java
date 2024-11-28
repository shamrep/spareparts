package com.spareparts.store.service.util.validation.rules;

public class ContainsUppercaseRule extends BaseRule<String> {
    public ContainsUppercaseRule(String s) {
    }

    @Override
    public boolean validate(String value) {
        return value.chars().anyMatch(Character::isUpperCase);
    }
}
