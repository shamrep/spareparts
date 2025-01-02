package com.spareparts.store.controller.actions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spareparts.store.controller.Request;
import com.spareparts.store.controller.Response;
import com.spareparts.store.service.ClientService;
import com.spareparts.store.service.ClientServiceImpl;
import com.spareparts.store.service.model.Client;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;

public class CreateClientHandler implements Handler {
    private final ClientService clientService;

    public CreateClientHandler() {
        this.clientService = new ClientServiceImpl();
    }

    @Override
    public void handle(Request request, Response response) {

        BufferedReader reader = null;
        try {
            reader = request.getReader();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        UserCredentials credentials = null;
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            credentials = objectMapper.readValue(reader, UserCredentials.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (clientService.findClientByEmail(credentials.email).isPresent()) {

            response.setStatus(HttpServletResponse.SC_CONFLICT);

            try {
                response.body(objectMapper.writeValueAsString(
                        new ErrorResponse(
                                HttpServletResponse.SC_CONFLICT,
                                "Email already registered",
                                credentials.email)));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        Client client = new Client(null, credentials.email, credentials.name, credentials.password, new HashSet<>());

        clientService.registerClient(client);
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    private static class UserCredentials {
        private String name;
        private String email;
        private String password;
    }

    @AllArgsConstructor
    private static class ErrorResponse {
        private int code;
        private String error;
        private String email;
    }
}
