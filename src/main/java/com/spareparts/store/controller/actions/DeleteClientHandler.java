package com.spareparts.store.controller.actions;

import com.spareparts.store.controller.Request;
import com.spareparts.store.controller.Response;
import com.spareparts.store.service.ClientAuthorizationService;
import com.spareparts.store.service.ClientServiceImpl;

public class DeleteClientHandler implements Handler {

    private final ClientServiceImpl clientService;
    private ClientAuthorizationService authorizationService;

    public DeleteClientHandler() {

        this.clientService = new ClientServiceImpl();
    }

    @Override
    public void handle(Request request, Response response) {

        //can I store permissions in caching

        long toDeleteClientId = 0;


        String authorizationHeader  = request.getHeader("Authorization");
        String token;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
        }




    }
}
