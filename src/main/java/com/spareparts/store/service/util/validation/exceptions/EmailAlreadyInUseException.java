package com.spareparts.store.service.util.validation.exceptions;

public class EmailAlreadyInUseException extends RuntimeException {
    public EmailAlreadyInUseException(String emailIsAlreadyRegistered) {
        super(emailIsAlreadyRegistered);
    }
}
