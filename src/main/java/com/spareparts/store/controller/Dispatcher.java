package com.spareparts.store.controller;

import com.spareparts.store.controller.actions.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Dispatcher {

    Map<Route, Handler> handlers = new HashMap<>();

    public Dispatcher() {

        handlers.put(new Route("/clients", Route.RequestMethods.GET), new GetAllClientsHandler());
        handlers.put(new Route("/client", Route.RequestMethods.POST), new CreateClientHandler());
        handlers.put(new Route("/client/login", Route.RequestMethods.POST), new LoginHandler());
        handlers.put(new Route("/client/{id}", Route.RequestMethods.DELETE), new DeleteClientHandler());
        handlers.put(new Route("/client/{clientId}/membership/{membershipId}", Route.RequestMethods.GET), new GetClientMembershipHandler());
//        handlers.put(new Route("/client/{clientId}/photos/{photoId}", Route.RequestMethods.GET), new GetClientPhotosHandler());
    }

    public void dispatch(Request request, Response response) {

       Handler handler = getHandler(Route.RequestMethods.valueOf(request.getMethod()), request.getPath());

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

    private Handler getHandler (Route.RequestMethods requestMethod, String requestPath) {

        Set<Map.Entry<Route, Handler>> entries = handlers.entrySet();

        for (Map.Entry<Route, Handler> entry : entries) {

            Route route = entry.getKey();

            if (route.getRequestMethod() == requestMethod) {

                String regex = route.getPathTemplate().replaceAll("\\{[^/]+}", "([^/]+)");

                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(requestPath);

                if (matcher.matches()) {
                    return entry.getValue();
                }
            }
        }

        return null;
    }
}
