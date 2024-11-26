package com.spareparts.store.service.util.validation;

import java.util.List;

public class ValidationResult {
    private final boolean valid;
    private final List<String> errorMessages;

    public ValidationResult(boolean valid, List<String> errorMessages) {
        this.valid = valid;
        this.errorMessages = List.copyOf(errorMessages); // Immutable copy
    }

    public boolean isValid() {
        return valid;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }
}
