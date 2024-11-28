package com.spareparts.store.service.util.validation.rules.email;

import com.spareparts.store.service.util.validation.rules.BaseRule;
import com.spareparts.store.service.util.validation.rules.ValidationRule;
import lombok.Getter;

import java.util.regex.Pattern;

public class EmailPatternRule extends BaseRule<String> {
    @Getter
    private String errorMessage = "Invalid email format.";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"
    );

    public EmailPatternRule() {
    }

    public EmailPatternRule(String errorMessage) {

        this.errorMessage = errorMessage;
    }

    @Override
    public boolean validate(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    @Override
    public String getMessage() {
        return "Invalid email format.";
    }
}
