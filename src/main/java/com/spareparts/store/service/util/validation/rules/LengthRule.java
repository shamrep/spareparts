package com.spareparts.store.service.util.validation.rules;

public class LengthRule implements ValidationRule<String>{

    public static final int MIN_LENGTH = 8;
    public static final int MAX_LENGTH = 20;

    public boolean validate(String value) {
        return value.length() >= MIN_LENGTH && value.length() <= MAX_LENGTH;
    }
}
