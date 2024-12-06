package com.spareparts.store.controller.actions;


import com.sun.net.httpserver.HttpExchange;

public interface Handler {
    HttpExchange handle(HttpExchange exchange);
}
