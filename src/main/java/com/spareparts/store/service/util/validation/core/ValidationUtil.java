package com.spareparts.store.service.util.validation.core;


import com.spareparts.store.service.util.validation.annotations.ValidEmail;
import com.spareparts.store.service.util.validation.annotations.ValidPassword;
import com.spareparts.store.service.util.validation.core.validators.BaseValidator;
import com.spareparts.store.service.util.validation.core.validators.Validator;
import com.spareparts.store.service.util.validation.exceptions.ValidationException;
import com.spareparts.store.service.util.validation.rules.*;
import com.spareparts.store.service.util.validation.rules.email.AllowedDomainsRule;
import com.spareparts.store.service.util.validation.rules.email.EmailPatternRule;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ValidationUtil {

    private final Map<Class<? extends Annotation>, BaseValidator<?>> validators = new HashMap<>();

    public ValidationUtil() {

        validators.put(ValidEmail.class, new BaseValidator<String>() {

            @Override
            protected List<BaseRule<String>> getRules() {
                return List.of(
                        new BlankRule("Email cannot be blank."),
                        new EmailPatternRule("Email must match the correct pattern."),
                        new AllowedDomainsRule("Email must be from an allowed domain."));
            }
        });

        validators.put(ValidPassword.class, new BaseValidator<String>() {

            @Override
            protected List<BaseRule<String>> getRules() {
                return List.of(
                        new BlankRule("Password cannot be null or blank."),
                        new LengthRule("Password must be between " + LengthRule.MIN_LENGTH + " and " + LengthRule.MAX_LENGTH + " characters."),
                        new ContainsLowercaseRule("Password must contain at least one lowercase letter."),
                        new ContainsSpecialCharacterRule("Password must contain at least one special character (e.g., !@#$%)."),
                        new ContainsUppercaseRule("Password must contain at least one uppercase letter."));
            }
        });
    }

    public Map<String, List<String>> validate(Object object) throws ValidationException {

        Map<String, List<String>> errors = new HashMap<>(); // Localized error map
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {

            field.setAccessible(true); // Allow access to private fields
            validateField(field, object, errors);
        }

        return errors;
    }

    private Object getFieldValue(Field field, Object object) {

        try {

            return field.get(object);

        } catch (IllegalAccessException e) {

            throw new IllegalStateException(
                    "Failed to access field '" + field.getName() + "' on object of type '" + object.getClass().getName() + "'",
                    e
            );
        }
    }

    private void validateField(Field field, Object object, Map<String, List<String>> errors) {

        Object value = getFieldValue(field, object);

        for (Annotation annotation : field.getAnnotations()) {
//            @SuppressWarnings("unchecked")
            Validator<Object> validator = (Validator<Object>) validators.get(annotation.annotationType());

            if (validator != null) {

                // Validate field value and collect errors
                List<String> fieldErrors = validator.validate(value);

                if (!fieldErrors.isEmpty()) {

                    errors.computeIfAbsent(field.getName(), k -> new ArrayList<>()).addAll(fieldErrors);
                }
            }
        }
    }
}

