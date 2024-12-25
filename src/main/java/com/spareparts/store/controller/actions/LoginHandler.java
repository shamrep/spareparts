package com.spareparts.store.controller.actions;

import com.spareparts.store.controller.Request;
import com.spareparts.store.controller.Response;
import com.spareparts.store.service.ClientService;
import com.sun.net.httpserver.HttpExchange;

public class LoginHandler implements  CustomHandler {

    private ClientService clientService;

    public LoginHandler(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public void handle(Request request, Response response) {
        String password = null;
        String email = null;

        clientService.validateCredentials(email, password);
    }
}
