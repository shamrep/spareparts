package com.spareparts.store.controller;

import com.spareparts.store.mapper.MapperException;

public class HandleError extends RuntimeException {
    public HandleError(MapperException e) {
    }
}
