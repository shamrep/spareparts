package com.spareparts.store.service.util.validation.rules;

public class BlankRule extends BaseRule<String> {
    public BlankRule(String message) {
        super(message);
    }

    @Override
    public boolean validate(String value) {
        return value != null && !value.trim().isEmpty();
    }

}
