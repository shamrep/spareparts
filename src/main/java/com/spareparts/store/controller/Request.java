package com.spareparts.store.controller;


import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
public class Request {

    private final HttpServletRequest baseRequest;

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

    public String getPathParameter(String parameterName) {

        String regex = baseRequest.getPathInfo().replaceAll("\\{([^/]+)}", "(?<$1>[^/]+)");
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher("dfsd");

        if (matcher.matches()) {
            // Return the captured value of the named group
            return matcher.group(parameterName);
        } else {
            throw new IllegalArgumentException("Path parameter not found: " + parameterName);
        }
    }
}
