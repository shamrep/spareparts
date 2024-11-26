package com.spareparts.store.service.util.validation;

import com.spareparts.store.service.util.validation.rules.AllowedDomainsRule;
import com.spareparts.store.service.util.validation.rules.BlankRule;
import com.spareparts.store.service.util.validation.rules.EmailPatternRule;
import com.spareparts.store.service.util.validation.rules.ValidationRule;

import java.util.ArrayList;
import java.util.List;

public class EmailValidator implements Validator<String> {

    private final List<ValidationRule<String>> rules = new ArrayList<>();

    public EmailValidator() {
        rules.add(new EmailPatternRule());
        rules.add(new BlankRule());
        rules.add(new AllowedDomainsRule("Email cannot be blank."));
    }

    @Override
    public List<String> validate(String email) {

        List<String> errors = rules.stream()
                .filter(rule -> !rule.validate(email))
                .map(ValidationRule::getMessage)
                .toList();

        return errors;
    }
}
