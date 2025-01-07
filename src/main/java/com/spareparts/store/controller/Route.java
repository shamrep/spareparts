package com.spareparts.store.controller;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Route {
    private String path;
    private RequestMethods requestMethod;

    public enum RequestMethods {
        GET,
        POST,
        PUT,
        DELETE
    }
}
