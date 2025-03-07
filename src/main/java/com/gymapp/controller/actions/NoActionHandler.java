package com.gymapp.controller.actions;

import com.gymapp.controller.Request;
import com.gymapp.controller.Response;

import java.util.Map;

public class NoActionHandler implements Handler {

    @Override
    public void handle(Request request, Response response) {

        response
                .setStatusCode(404)
                .error("Not Found")
                .message("The requested resource was not found on the server.")
                .details(Map.of("path", request.getRequestURI().toString(),"timestamp", java.time.Instant.now().toString()))
                .build();
    }
}
