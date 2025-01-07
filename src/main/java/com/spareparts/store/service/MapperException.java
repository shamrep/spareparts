package com.spareparts.store.service;

import java.util.Map;

public class MapperException extends RuntimeException {
    public MapperException(String message) {
        super(message);
    }

    public Map<String, String> getDetails() {
        return null;
    }
}
