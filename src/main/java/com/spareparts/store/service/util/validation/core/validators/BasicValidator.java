package com.spareparts.store.service.util.validation.core.validators;

import com.spareparts.store.service.util.validation.annotations.ValidEmail;
import com.spareparts.store.service.util.validation.annotations.ValidPassword;
import com.spareparts.store.service.util.validation.rules.AbstractRule;
import com.spareparts.store.service.util.validation.rules.BlankRule;
import com.spareparts.store.service.util.validation.rules.email.AllowedDomainsRule;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

public class BasicValidator implements Validator<Object> {

    private final Map<Class<? extends Annotation>, List<AbstractRule<?>>> annotationRulesMap = new HashMap<>();

    public BasicValidator() {
        // Map annotations to their validation rules
        annotationRulesMap.put(ValidEmail.class, List.of(
                new AllowedDomainsRule("Email must be from an allowed domain.")
        ));
        annotationRulesMap.put(ValidPassword.class, List.of(
                new BlankRule("Password cannot be null or blank.")
        ));
    }

    @Override
    public Map<String, List<String>> validate(Object objectToValidate) {

        Map<String, List<String>> validationErrorsMap = new HashMap<>();
        Field[] fields = objectToValidate.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true); // Allow access to private fields
            validateField(field, objectToValidate, validationErrorsMap);
        }

        return validationErrorsMap;
    }

    private void validateField(Field field, Object objectToValidate, Map<String, List<String>> errors) {

        Object fieldValue = getFieldValue(field, objectToValidate);
        Annotation[] annotations = field.getAnnotations();

        for (Annotation annotation : annotations) {
            // Retrieve rules for the annotation
            List<AbstractRule<?>> rules = annotationRulesMap.getOrDefault(annotation.annotationType(), Collections.emptyList());
            List<String> fieldErrors = new ArrayList<>();

            for (AbstractRule<?> rule : rules) {

                if (!validateRule(rule, fieldValue)) {

                    fieldErrors.add(rule.getMessage());
                }
            }

            if (!fieldErrors.isEmpty()) {

                errors.computeIfAbsent(field.getName(), k -> new ArrayList<>()).addAll(fieldErrors);
            }
        }
    }

    private Object getFieldValue(Field field, Object objectToValidate) {
        try {

            return field.get(objectToValidate);

        } catch (IllegalAccessException e) {
            throw new IllegalStateException(

                    "Failed to access field '" + field.getName() + "' on object of type '" + objectToValidate.getClass().getName() + "'",
                    e
            );
        }
    }

    @SuppressWarnings("unchecked")
    private <T> boolean validateRule(AbstractRule<T> rule, Object value) {

        try {

            return rule.validate((T) value);

        } catch (ClassCastException e) {

            throw new IllegalArgumentException(
                    "Validation rule is incompatible with the field type. Rule: " + rule.getClass().getName() + ", Value: " + value,
                    e
            );
        }
    }
}
