package com.spareparts.store.service.util.validation.rules;

public class BlankRule implements ValidationRule<String> {

    private final String message;

    public BlankRule() {

        message =  "Must not be blank.";
    }

    public BlankRule(String message) {

        this.message = message;
    }

    @Override
    public boolean validate(String value) {

        return value != null && !value.isBlank();
    }

    @Override
    public String getMessage() {

        return message;
    }
}
