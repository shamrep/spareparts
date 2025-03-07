package com.gymapp.controller;

import com.gymapp.mapper.MapperException;

public class HandleError extends RuntimeException {
    public HandleError(MapperException e) {
    }
}
