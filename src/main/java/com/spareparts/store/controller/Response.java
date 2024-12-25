package com.spareparts.store.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import java.io.IOException;


@AllArgsConstructor
public class Response {

    private final HttpServletResponse baseResponse;

    public void setStatus(int sc) {

        baseResponse.setStatus(sc);
    }

    public void setContentType(String type) {

        baseResponse.setContentType(type);
    }

    public void writeJsonResponse(String json) {

        baseResponse.setContentType("application/json");
        baseResponse.setStatus(HttpServletResponse.SC_OK);

        try {
            baseResponse.getWriter().print(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
