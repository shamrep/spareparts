package com.spareparts.store.controller;


import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;

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

        try {
            return baseRequest.getReader();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getMethod() {

        return baseRequest.getMethod();
    }

    public Object getRequestURI() {

        return baseRequest.getRequestURI();
    }

    public String getBody() {
        StringBuilder requestBody = new StringBuilder();
        String line;

        try (BufferedReader reader = baseRequest.getReader()) {
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return requestBody.toString();
    }
}
