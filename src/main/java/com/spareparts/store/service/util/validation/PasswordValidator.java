package com.spareparts.store.service.util.validation;

import com.spareparts.store.service.util.validation.rules.*;

import java.util.ArrayList;
import java.util.List;

public class PasswordValidator implements Validator<String> {

    private final List<PasswordValidationRule> rules = new ArrayList<>();

    public PasswordValidator() {
        rules.add(new PasswordValidationRule(new BlankRule(), "Password cannot be null or blank."));
        rules.add(new PasswordValidationRule(new LengthRule(), "Password must be between "
                                                               + LengthRule.MIN_LENGTH + " and " + LengthRule.MAX_LENGTH + " characters."));
        rules.add(new PasswordValidationRule(new ContainsLowercase(), "Password must contain at least one lowercase letter."));
        rules.add(new PasswordValidationRule(new ContainsSpecialCharacter(), "Password must contain at least one special character (e.g., !@#$%)."));
        rules.add(new PasswordValidationRule(new ContainsUppercaseRule(), "Password must contain at least one uppercase letter."));
    }

    @Override
    public List<String> validate(String password) {

        List<String> errors = rules.stream()
                .filter(rule -> !rule.validate(password))
                .map(PasswordValidationRule::errorMessage)
                .toList();

        return errors;
    }

    private record PasswordValidationRule(ValidationRule<String> rule, String errorMessage) {

        public boolean validate(String password) {
            return rule.validate(password);
        }
    }
}
