package com.spareparts.store.service.util.validation.rules;

public class LengthRule extends BaseRule<String> {

    public static final int MIN_LENGTH = 8;
    public static final int MAX_LENGTH = 20;

    public LengthRule(String message) {
        super(message);
    }

    @Override
    public boolean validate(String value) {
        return value.length() >= MIN_LENGTH && value.length() <= MAX_LENGTH;
    }
}
