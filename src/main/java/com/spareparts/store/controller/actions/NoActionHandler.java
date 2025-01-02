package com.spareparts.store.controller.actions;

import com.spareparts.store.controller.Request;
import com.spareparts.store.controller.Response;

public class NoActionHandler implements Handler {

    @Override
    public void handle(Request request, Response response) {

        String jsonResponse = """
                {
                    "timestamp": "%s",
                    "status": 404,
                    "error": "Not Found",
                    "message": "The requested resource was not found on the server.",
                    "path": "%s"
                }
                """.formatted(
                java.time.Instant.now().toString(),
                request.getRequestURI()
        );

        response.setStatus(404);
        response.body(jsonResponse);
    }
}
