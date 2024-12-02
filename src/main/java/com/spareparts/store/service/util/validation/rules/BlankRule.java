package com.spareparts.store.service.util.validation.rules;

public class BlankRule extends AbstractRule<String> {
    public BlankRule(String message) {
        super(message);
    }

    public BlankRule() {
        super("Must not be blank.");
    }

    @Override
    public boolean validate(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
