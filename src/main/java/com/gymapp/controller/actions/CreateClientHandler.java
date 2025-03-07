package com.gymapp.controller.actions;

import com.gymapp.controller.Request;
import com.gymapp.controller.Response;
import com.gymapp.controller.dto.ClientRegistrationDTO;
import com.gymapp.mapper.ClientMapper;
import com.gymapp.mapper.JsonMapper;
import com.gymapp.mapper.MapperException;
import com.gymapp.service.ClientAuthenticationService;
import com.gymapp.service.ClientService;
import com.gymapp.service.model.Client;
import com.gymapp.service.util.validation.exceptions.EmailAlreadyInUseException;
import com.gymapp.service.util.validation.exceptions.ValidationException;

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

//             Register client
//            Client registeredClient = clientService.registerClient(client)
//                    .orElseThrow(() -> new RuntimeException("Client registration failed"));

            Client registeredClient = clientService.registerClient(client).get();

            // Generate authentication token
            String clientToken = clientAuthenticationService.generateClientToken(registeredClient);

//             Successful response
            response
                    .setStatusCode(Response.SC_CREATED)
                    .message("Registration is successful.")
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
                    .error("Registration process failed")
                    .setStatusCode(Response.SC_INTERNAL_SERVER_ERROR)
                    .message("Unexpected error during registration")
                    .build();
        }
    }

}