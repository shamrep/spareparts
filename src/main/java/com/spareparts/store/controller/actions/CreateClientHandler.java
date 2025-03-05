package com.spareparts.store.controller.actions;

import com.spareparts.store.controller.Request;
import com.spareparts.store.controller.Response;
import com.spareparts.store.controller.dto.ClientRegistrationDTO;
import com.spareparts.store.mapper.ClientMapper;
import com.spareparts.store.mapper.JsonMapper;
import com.spareparts.store.mapper.MapperException;
import com.spareparts.store.service.ClientAuthenticationService;
import com.spareparts.store.service.ClientService;
import com.spareparts.store.service.model.Client;
import com.spareparts.store.service.util.validation.exceptions.EmailAlreadyInUseException;
import com.spareparts.store.service.util.validation.exceptions.ValidationException;

import java.util.Map;

public class CreateClientHandler implements Handler {

    private final ClientService clientService;
    private final ClientMapper clientMapper;
    private final ClientAuthenticationService clientAuthenticationService;

    public CreateClientHandler(
            ClientService clientService,
            ClientMapper clientMapper,
            ClientAuthenticationService clientAuthenticationService
    ) {
        this.clientService = clientService;
        this.clientMapper = clientMapper;
        this.clientAuthenticationService = clientAuthenticationService;
    }

    public CreateClientHandler() {

        this(new ClientService(), new ClientMapper(), new ClientAuthenticationService());

    }

    @Override
    public void handle(Request request, Response response) {

        try {
            // Validate and map input
            ClientRegistrationDTO registrationDTO = JsonMapper.fromJson(request.getBody(), ClientRegistrationDTO.class);
            Client client = clientMapper.toClient(registrationDTO);

            // Register client
            Client registeredClient = clientService.registerClient(client)
                    .orElseThrow(() -> new RuntimeException("Client registration failed"));

            // Generate authentication token
            String clientToken = clientAuthenticationService.generateClientToken(registeredClient);

            // Successful response
            response
                    .setStatusCode(Response.SC_CREATED)
                    .message("Authorization successful.")
                    .details(Map.of(
                            "token", clientToken,
                            "expiresIn", clientAuthenticationService.getExpirationDate().toString()
                    ))
                    .build();

        } catch (MapperException e) {
            // Input mapping error
            response
                    .error("Validation Error")
                    .setStatusCode(Response.SC_BAD_REQUEST)
                    .message("Invalid input data")
                    .build();

        } catch (ValidationException e) {
            // Validation error
            response
                    .error("Validation Error")
                    .setStatusCode(Response.SC_BAD_REQUEST)
                    .message(e.getMessage())
                    .build();

        } catch (EmailAlreadyInUseException e) {
            // Duplicate email error
            response
                    .setStatusCode(Response.SC_CONFLICT)
                    .message("Client registration failed.")
                    .error("Email already registered.")
                    .build();

        } catch (RuntimeException e) {
            // Unexpected registration failure
            response
                    .setStatusCode(Response.SC_INTERNAL_SERVER_ERROR)
                    .message("Unexpected error during registration")
                    .error("Registration process failed")
                    .build();
        }
    }
}