package com.spareparts.store.controller;

import com.spareparts.store.controller.actions.Handler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FrontController implements HttpHandler {

    private final Map<String, Handler> handlersMapping = new HashMap<>();
    private final Dispatcher dispatcher;

    public FrontController() {
        dispatcher = new DispatcherImpl();
    }


    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String path = exchange.getRequestURI().getPath();
        String requestMethod = exchange.getRequestMethod();

        Handler handler = dispatcher.dispatch(path, requestMethod);

        if(handler != null) {
            handler.handle(exchange);
            exchange.getResponseHeaders().add("Content-Type", "application/json");
        }
        exchange.getResponseBody().close();
    }


    private void sendNotFoundResponse(HttpExchange exchange) throws IOException {
        String response = "404 Not Found";
        exchange.sendResponseHeaders(404, response.length());
        exchange.getResponseBody().write(response.getBytes());
        exchange.getResponseBody().close();
    }

}
