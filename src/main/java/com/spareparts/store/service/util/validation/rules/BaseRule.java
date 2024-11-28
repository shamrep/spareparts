package com.spareparts.store.service.util.validation.rules;

import lombok.Getter;

@Getter
public abstract class BaseRule<T> implements ValidationRule<T> {

    private final String message;

    public BaseRule(String message) {
        this.message = message;
    }

    public BaseRule() {
        this.message = "Invalid value.";
    }
}
