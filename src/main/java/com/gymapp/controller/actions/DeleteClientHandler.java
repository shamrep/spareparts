package com.gymapp.controller.actions;

import com.gymapp.controller.Request;
import com.gymapp.controller.Response;
import com.gymapp.service.ClientAuthorizationService;
import com.gymapp.service.ClientService;

public class DeleteClientHandler implements Handler {

    private final ClientService clientService;
    private ClientAuthorizationService authorizationService;
    private static String PERMISSION = "deleteClient";

    public DeleteClientHandler() {

        this.clientService = new ClientService();
    }

    @Override
    public void handle(Request request, Response response) {


        long toDeleteClientId = Long.getLong(request.getPathParameter("clientId"));


        String authorizationHeader  = request.getHeader("Authorization");
        String token;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
        }

        //todo:  add permission check
        clientService.deleteClient(toDeleteClientId);

    }
}
