package com.spareparts.store.controller.actions;

import com.spareparts.store.controller.HandleError;
import com.spareparts.store.controller.Request;
import com.spareparts.store.controller.Response;
import com.spareparts.store.mapper.JsonMapper;
import com.spareparts.store.mapper.MapperException;
import com.spareparts.store.service.ClientAuthorizationService;
import com.spareparts.store.service.ClientService;
import com.spareparts.store.service.ClientServiceImpl;
import com.spareparts.store.service.model.Client;
import com.spareparts.store.service.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.Set;

public class LoginHandler implements Handler {

    private final ClientService clientService;
    private final ClientAuthorizationService clientAuthorizationService;

    public LoginHandler() {

        this.clientService = new ClientServiceImpl();
        this.clientAuthorizationService = new ClientAuthorizationService();

    }

    @Override
    public void handle(Request request, Response response) {

        String body = request.getBody();
        CredentialsDTO credentialsDto = null;

        try {

            credentialsDto = JsonMapper.fromJson(body, CredentialsDTO.class);

        } catch (MapperException e) {

            response
                    .setStatusCode(Response.SC_BAD_REQUEST)
                    .error("Bad Request")
                    .message("Invalid request body")
                    .details(e.getMessage())
                    .build();

            return;

        }

        Optional<Client> optionalClient
                = clientService.loginClient(credentialsDto.getEmail(), credentialsDto.getPassword());

        if (optionalClient.isPresent()) {

            String token = clientAuthorizationService.generateToken(optionalClient.get());
            OffsetDateTime expirationDate = clientAuthorizationService.getExpirationDate();

            LoginResponse loginResponse = new LoginResponse(
                    token,
                    expirationDate,
                    new LoginResponse.UserDetails(
                            optionalClient.get().getId(),
                            optionalClient.get().getEmail(),
                            optionalClient.get().getName(),
                            optionalClient.get().getRoles()));

            try {

                response
                        .setStatusCode(Response.SC_OK)
                        .setHeader("Authorization", String.format("Bearer %s", token))
                        .body(JsonMapper.toJson(loginResponse))
                        .build();

            } catch (MapperException e) {

                throw new HandleError(e);

            }

        } else {

            response
                    .setStatusCode(Response.SC_NOT_AUTHORIZED)
                    .error("Unauthorized")
                    .message("Invalid email or password")
                    .build();

        }
    }

    @Getter
    @AllArgsConstructor
    private static class LoginResponse {

        private String token;
        private OffsetDateTime expirationDate;
        private UserDetails user;

        @Getter
        @AllArgsConstructor
        public static class UserDetails {

            private long id;
            private String email;
            private String name;
            private Set<Role> roles;

        }
    }

    @AllArgsConstructor
    @Getter
    private static class CredentialsDTO {

        private String email;
        private String password;

    }
}
