package com.spareparts.store.controller.actions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spareparts.store.controller.Request;
import com.spareparts.store.controller.Response;
import com.spareparts.store.service.ClientService;
import com.spareparts.store.service.ClientServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;

public class CreateClientHandler implements Handler {
    private final ClientService clientService;

    public CreateClientHandler() {
        this.clientService = new ClientServiceImpl();
    }

    @Override
    public void handle(Request request, Response response) {

        BufferedReader reader = request.getReader();
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
                response.writeJsonResponse(objectMapper.writeValueAsString(
                        new ErrorResponse(
                                HttpServletResponse.SC_CONFLICT,
                                "Email already registered",
                                credentials.email)));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @AllArgsConstructor
    private static class UserCredentials {
        private String username;
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
