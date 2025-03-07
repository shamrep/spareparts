package com.gymapp.service.util.validation.exceptions;

public class EmailAlreadyInUseException extends RuntimeException {
    public EmailAlreadyInUseException(String emailIsAlreadyRegistered) {
        super(emailIsAlreadyRegistered);
    }
}
