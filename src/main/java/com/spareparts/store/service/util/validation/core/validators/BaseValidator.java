package com.spareparts.store.service.util.validation.core.validators;

import com.spareparts.store.service.util.validation.rules.BaseRule;
import com.spareparts.store.service.util.validation.rules.ValidationRule;

import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseValidator<T> implements Validator<T> {

    private List<BaseRule<T>> rules;

    protected abstract List<BaseRule<T>> getRules();


    @Override
    public List<String> validate(T value) {
        return rules.stream().filter(tBaseRule -> !tBaseRule.validate(value)).map(BaseRule::getMessage).toList();
    }

    @Override
    public boolean isValid(T value) {
        return Validator.super.isValid(value);
    }
}
