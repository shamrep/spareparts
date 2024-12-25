package com.spareparts.store.controller;


import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

import java.io.BufferedReader;

@AllArgsConstructor
public class Request {
    private final HttpServletRequest
            baseRequest;

    public String getHeader(String name) {

        return baseRequest.getHeader(name);
    }

    public String getPath() {

        return baseRequest.getPathInfo();
    }

    public BufferedReader getReader() {

        return null;
    }

    public String getMethod() {

        return baseRequest.getMethod();
    }

    public Object getRequestURI() {

        return baseRequest.getRequestURI();
    }
}
