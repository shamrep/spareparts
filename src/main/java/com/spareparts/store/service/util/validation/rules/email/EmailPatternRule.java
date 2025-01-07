package com.spareparts.store.service.util.validation.rules.email;

import com.spareparts.store.service.util.validation.rules.AbstractRule;
import com.spareparts.store.service.util.validation.rules.ValidationRule;
import lombok.Getter;

import java.util.regex.Pattern;

public class EmailPatternRule extends AbstractRule<String> {

    public EmailPatternRule(String message) {

    }

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"
    );

    @Override
    public boolean validate(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    @Override
    public String toString() {
        return "Invalid email format.";
    }
}
