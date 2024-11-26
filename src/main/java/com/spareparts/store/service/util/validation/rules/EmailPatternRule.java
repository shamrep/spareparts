package com.spareparts.store.service.util.validation.rules;

import lombok.Getter;

import java.util.regex.Pattern;

public class EmailPatternRule implements ValidationRule <String>{
    @Getter
    private String errorMessage = "Invalid email format.";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"
    );

    @Override
    public boolean validate(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    @Override
    public String getMessage() {
        return "Invalid email format.";
    }
}
