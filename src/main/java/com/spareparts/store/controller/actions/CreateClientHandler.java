package com.spareparts.store.controller.actions;

import com.spareparts.store.controller.Request;
import com.spareparts.store.controller.Response;
import com.spareparts.store.mapper.ClientMapperImpl;
import com.spareparts.store.mapper.JsonMapper;
import com.spareparts.store.mapper.MapperException;
import com.spareparts.store.service.ClientAuthorizationService;
import com.spareparts.store.service.ClientServiceImpl;
import com.spareparts.store.service.model.Client;
import com.spareparts.store.service.model.ClientCredentials;
import com.spareparts.store.service.util.validation.exceptions.ValidationException;

import java.util.Map;
import java.util.Optional;

public class CreateClientHandler implements Handler {

    private final ClientServiceImpl clientService;
    private final ClientMapperImpl clientMapper = new ClientMapperImpl();
    private final ClientAuthorizationService clientAuthorizationService;


    public CreateClientHandler() {

        this.clientService = new ClientServiceImpl();
        this.clientAuthorizationService = new ClientAuthorizationService();
    }

    @Override
    public void handle(Request request, Response response) {

        try {

            ClientCredentials credentials = JsonMapper.fromJson(request.getBody(), ClientCredentials.class);
            Client client = clientMapper.toClient(credentials);

            Optional<Client> registeredClient = clientService.registerClient(client);

            if (registeredClient.isPresent()) {
                String clientToken = clientAuthorizationService.generateToken(registeredClient.get());

                response
                        .setStatusCode(Response.SC_CREATED)
                        .message("Authorization successful.")
                        .details(
                                Map.of("token", clientToken,
                                        "expiresIn", clientAuthorizationService.getExpirationDate().toString()))
                        .build();

            }

        } catch (MapperException e) {

            response
                    .error("Validation Error")
                    .setStatusCode(Response.SC_BAD_REQUEST)
                    .message("Invalid input data")
                    .build();

        } catch (ValidationException e) {

            response
                    .error("Validation Error")
                    .setStatusCode(Response.SC_BAD_REQUEST)
                    .message(e.getMessage())
//                    .details()
                    .build();
        }

    }
}
