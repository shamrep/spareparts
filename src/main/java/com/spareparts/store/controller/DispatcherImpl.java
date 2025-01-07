package com.spareparts.store.controller;

import com.spareparts.store.controller.actions.*;

import java.util.HashMap;
import java.util.Map;

public class DispatcherImpl implements Dispatcher {

    Map<Route, Handler> handlers = new HashMap<>();

    public DispatcherImpl() {

        handlers.put(new Route("/clients", Route.RequestMethods.GET), new GetAllClientsHandler());
        handlers.put(new Route("/client", Route.RequestMethods.POST), new CreateClientHandler());
        handlers.put(new Route("/client/login", Route.RequestMethods.POST), new LoginHandler());
        handlers.put(new Route("/client/{id}", Route.RequestMethods.DELETE), new DeleteClientHandler());
    }

    @Override
    public void dispatch(Request request, Response response) {

        String pathTemplate = PathParser.transformToTemplate(request.getPath());

        Route route = new Route(pathTemplate, Route.RequestMethods.valueOf(request.getMethod()));
        Handler handler = handlers.get(route);

        try {

            if (handler != null) {

                handler.handle(request, response);

            } else {

                new NoActionHandler().handle(request, response);

            }
        } catch (Exception e) {

            new InternalErrorHandler().handle(request, response);
        }
    }
}
