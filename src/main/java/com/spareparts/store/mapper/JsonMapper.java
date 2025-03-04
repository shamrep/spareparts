package com.spareparts.store.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMapper {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> T fromJson(String json, Class<T> clazz) throws MapperException {
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new MapperException(e);
        }
    }

    public static <T> String toJson(T t) throws MapperException {
        try {
            return OBJECT_MAPPER.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            throw new MapperException(e);
        }
    }
}
