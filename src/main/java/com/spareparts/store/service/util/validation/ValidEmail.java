package com.spareparts.store.service.util.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD) // Specifies that this annotation can only be used on fields
@Retention(RetentionPolicy.RUNTIME) // The annotation is retained at runtime
public @interface ValidEmail {

    String message() default "Invalid email format"; // Error message
}
