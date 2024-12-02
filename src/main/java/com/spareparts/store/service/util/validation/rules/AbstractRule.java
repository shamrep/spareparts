package com.spareparts.store.service.util.validation.rules;

public abstract class AbstractRule <T> implements ValidationRule <T> {

    private final String message;

    public AbstractRule() {
        message = "Invalid data.";
    }

    public AbstractRule(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
