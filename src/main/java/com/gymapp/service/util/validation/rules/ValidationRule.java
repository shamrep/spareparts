package com.gymapp.service.util.validation.rules;

@FunctionalInterface
public interface ValidationRule<T> {
    boolean validate(T value);

//    default String getMessage() {
//        return  "Validation failed.";
//    }
}
