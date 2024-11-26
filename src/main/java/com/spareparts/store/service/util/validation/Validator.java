package com.spareparts.store.service.util.validation;

import java.util.List;

public interface Validator<T> {

    List<String> validate(T value);

    default boolean isValid(T value) {
        return validate(value).isEmpty();
    }
}
