package com.spareparts.store.controller;

import com.spareparts.store.mapper.Mapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


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

    public static int SC_CONFLICT = 409;

    public static int SC_BAD_REQUEST = 400;

    public static int SC_INTERNAL_SERVER_ERROR = 111;

    private final HttpServletResponse baseResponse;

    Map<String, String> details = new HashMap<>();

    private int statusCode;

    public Response(HttpServletResponse baseResponse) {
        this.baseResponse = baseResponse;
    }

    public Response setStatusCode(int sc) {

        baseResponse.setStatus(sc);
        statusCode = sc;

        return this;
    }

    public Response setContentType(String type) {

        baseResponse.setContentType(type);

        return this;
    }

    public Response body(Map<String, String> claims) {

        writeJsonBody(Mapper.toJson(claims));

        return this;
    }

    public Response body(String json) {

        writeJsonBody(json);

        return this;
    }

    public Response error(String error) {

        details.put("error", error);

        return this;
    }

    public Response message(String message) {

        details.put("message", message);

        return this;
    }

    public Response details(Map<String, String> claims) {

        details.putAll(claims);

        return this;
    }

    private void writeJsonBody(String json) {

        try {
            baseResponse.getWriter().print(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void build() {
        baseResponse.setContentType("application/json");
        details.put("status", Integer.toString(statusCode));

        writeJsonBody(Mapper.toJson(details));
    }
}
