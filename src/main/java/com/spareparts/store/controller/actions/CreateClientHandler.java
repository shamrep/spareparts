package com.spareparts.store.controller.actions;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class CreateClientHandler implements Handler {


    @Override
    public HttpExchange handle(HttpExchange exchange) {
        // Read request body
        String requestBody = null;
        try {
            requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Process request (e.g., save to database, etc.)
        System.out.println("Received client data: " + requestBody);

        String response = "Client created successfully!";
        try {
            exchange.sendResponseHeaders(201, response.length());
            exchange.getResponseBody().write(response.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return exchange;
    }
}
