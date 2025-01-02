package com.spareparts.store.controller;

import com.spareparts.store.controller.actions.*;

import java.util.HashMap;
import java.util.Map;

public class DispatcherImpl implements Dispatcher {

    Map<Route, Handler> handlers = new HashMap<>();

    public DispatcherImpl() {

        handlers.put(new Route("/clients", "GET"), new GetAllClientsHandler());
        handlers.put(new Route("/client", "POST"), new CreateClientHandler());
        handlers.put(new Route("/client/login", "POST"), new LoginHandler());
    }

    @Override
    public void dispatch(Request request, Response response) {

        Route route = new Route(request.getPath(), request.getMethod());
        Handler handler = handlers.get(route);

        if (handler != null) {
            handler.handle(request, response);
        } else {
            new NoActionHandler().handle(request, response);
        }
    }
}
