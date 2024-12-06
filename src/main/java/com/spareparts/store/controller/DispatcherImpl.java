package com.spareparts.store.controller;

import com.spareparts.store.controller.actions.CreateClientHandler;
import com.spareparts.store.controller.actions.Handler;
import com.spareparts.store.controller.actions.GetAllClientsHandler;

import java.util.HashMap;
import java.util.Map;

public class DispatcherImpl implements Dispatcher {

    Map<Route, Handler> handlers = new HashMap<>();

    public DispatcherImpl() {
        handlers.put(new Route("clients", "GET"), new GetAllClientsHandler());
        handlers.put(new Route("client", "POST"), new CreateClientHandler());

    }

    @Override
    public Handler dispatch(String path, String requestMethod) {

        RestUrlParser.ParsedUrl parsedUrl = RestUrlParser.parse(path);

        return handlers.get(new Route(parsedUrl.getPathVariables().getFirst(), requestMethod));
    }
}
