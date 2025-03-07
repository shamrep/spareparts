package com.gymapp.controller.actions;

import com.gymapp.controller.Request;
import com.gymapp.controller.Response;

public class InternalErrorHandler implements Handler {

    @Override
    public void handle(Request request, Response response) {

        response
                .setStatusCode(Response.SC_INTERNAL_SERVER_ERROR)
                .error("Internal Server Error")
                .message("An unexpected error occurred.")
                .build();
    }
}
