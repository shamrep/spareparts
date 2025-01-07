package com.spareparts.store.service.util.validation.exceptions;

import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class ValidationException extends RuntimeException {
    private  Map<String, List<String>> errors;
    private  Map<String, String> errors_;

    public ValidationException(String message, Map<String, List<String>> errors) {
        super(message);
        this.errors = errors;
    }

    public ValidationException(Map<String, String> errors) {
        this.errors_ = errors;
    }
}
