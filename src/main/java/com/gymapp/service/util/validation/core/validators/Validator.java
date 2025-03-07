package com.gymapp.service.util.validation.core.validators;

import java.util.List;
import java.util.Map;

public interface Validator<T> {

    Map<String, List<String>> validate(T value);

    default boolean isValid(T value) {
        return validate(value).isEmpty();
    }
}
