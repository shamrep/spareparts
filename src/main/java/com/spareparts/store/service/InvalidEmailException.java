package com.spareparts.store.service;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(String invalidEmailAddress, IllegalAccessException e) {
    }
}
