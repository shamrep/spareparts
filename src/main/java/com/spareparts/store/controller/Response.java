package com.spareparts.store.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.Map;


@AllArgsConstructor
public class Response {
    public static int SC_CONTINUE = 100;

    /**
     * Status code (101) indicating the server is switching protocols according to Upgrade header.
     */
    public static int SC_SWITCHING_PROTOCOLS = 101;

    /**
     * Status code (200) indicating the request succeeded normally.
     */
    public static int SC_OK = 200;

    /**
     * Status code (201) indicating the request succeeded and created a new resource on the server.
     */
    public static int SC_CREATED = 201;

    /**
     * Status code (202) indicating that a request was accepted for processing, but was not completed.
     */
    public static int SC_ACCEPTED = 202;

    public static int SC_NOT_AUTHORIZED = 401;

    private final HttpServletResponse baseResponse;

    public Response setStatus(int sc) {

        baseResponse.setStatus(sc);
        return this;
    }

    public Response setContentType(String type) {

        baseResponse.setContentType(type);
        return this;
    }

    public void body(String json) {

        baseResponse.setStatus(HttpServletResponse.SC_OK);

        try {
            baseResponse.getWriter().print(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void body(Map<String, Object> errorResponse) {
//        baseResponse.sendError((Integer) errorResponse.get("status"));
    }
}
