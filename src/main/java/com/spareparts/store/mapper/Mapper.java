package com.spareparts.store.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spareparts.store.repository.entity.RoleEntity;
import com.spareparts.store.service.model.Role;

import java.io.BufferedReader;
import java.io.IOException;

public class Mapper {

   static ObjectMapper objectMapper = new ObjectMapper();

    public static <T>  T readValue(BufferedReader reader, Class<T> tClass) {
        try {
            return objectMapper.readValue(reader, tClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> String toJson(T value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
