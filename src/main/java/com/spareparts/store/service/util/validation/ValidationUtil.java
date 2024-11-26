package com.spareparts.store.service.util.validation;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ValidationUtil {

    private final Map<Class<? extends Annotation>, Validator<?>> validators = new HashMap<>();

    public ValidationUtil() {

        validators.put(ValidEmail.class, new EmailValidator());
        validators.put(ValidPassword.class, new PasswordValidator());
    }

    public boolean validate(Object object) throws ValidationException {

        Map<String, List<String>> errors = new HashMap<>(); // Localized error map
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {

            field.setAccessible(true); // Allow access to private fields
            validateField(field, object, errors);
        }

        if (!errors.isEmpty()) {

            throw new ValidationException("Validation failed", errors);
        }

        return true;
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

