package com.gymapp.service.util.validation.rules;

public class ContainsDigitRule extends AbstractRule<String> implements ValidationRule<String> {
    public ContainsDigitRule() {
        super("Should contain at least one digit.");
    }

    public ContainsDigitRule(String s) {
        super(s);
    }

    @Override
    public boolean validate(String value) {
        return value.chars().anyMatch(Character::isDigit);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
