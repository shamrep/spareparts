package com.spareparts.store.service;

import com.spareparts.store.service.model.ClientCredentials;
import com.spareparts.store.service.util.validation.core.validators.BasicValidator;
import com.spareparts.store.service.util.validation.exceptions.ValidationException;

import java.util.List;
import java.util.Map;

public class ClientValidationService {
    private BasicValidator validator;

    public ClientValidationService() {
        this.validator = new BasicValidator();
    }

    public void validateCredentials(ClientCredentials credentials) {
        Map<String, List<String>> errors = validator.validate(credentials);

        if(!errors.isEmpty()) {
            throw new ValidationException("Invalid credentials", errors);
        }
    }
}
