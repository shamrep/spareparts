package com.spareparts.store.service.util.validation;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

public class EmailValidator {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"
    );

    public static void validate(Object object) throws ValidationException {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(ValidEmail.class)) {
                field.setAccessible(true); // Access private fields
                Object value = null;

                try {
                    value = field.get(object);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }

                if (value instanceof String email) {
                    if (!EMAIL_PATTERN.matcher(email).matches()) {
                        ValidEmail annotation = field.getAnnotation(ValidEmail.class);
                        throw new ValidationException(annotation.message());
                    }
                } else {
                    throw new ValidationException("Field annotated with @ValidEmail is not of type String");
                }
            }
        }
    }
}
