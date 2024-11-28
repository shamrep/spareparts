package com.spareparts.store.service;

public class EmailAlreadyInUseException extends RuntimeException {
    public EmailAlreadyInUseException(String emailIsAlreadyRegistered) {
        super(emailIsAlreadyRegistered);
    }
}
