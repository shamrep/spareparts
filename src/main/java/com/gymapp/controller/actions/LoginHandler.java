package com.gymapp.controller.actions;

import com.gymapp.controller.Request;
import com.gymapp.controller.Response;
import com.gymapp.mapper.JsonMapper;
import com.gymapp.mapper.MapperException;
import com.gymapp.service.ClientAuthenticationService;
import com.gymapp.service.model.Client;
import com.gymapp.service.model.ClientRole;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
public class LoginHandler implements Handler {

    private final ClientAuthenticationService clientAuthenticationService;

    // Constructor with default service creation can be used if dependency injection is not available
    public LoginHandler() {
        this(new ClientAuthenticationService());
    }

    @Override
    public void handle(Request request, Response response) {

        try {
            CredentialsDTO credentialsDto = parseCredentials(request.getBody());
            authenticateAndRespond(credentialsDto, response);
        } catch (MapperException e) {
            handleInvalidRequestBody(response, e);
        }

    }

    private CredentialsDTO parseCredentials(String body) throws MapperException {

        return JsonMapper.fromJson(body, CredentialsDTO.class);

    }

    private void authenticateAndRespond(CredentialsDTO credentialsDto, Response response) throws MapperException {

        Optional<Client> optionalClient = clientAuthenticationService.authenticateClient(
                credentialsDto.getEmail(),
                credentialsDto.getPassword()
        );

        if (optionalClient.isPresent()) {
            handleSuccessfulLogin(optionalClient.get(), response);
        } else {
            handleUnauthorizedLogin(response);
        }

    }

    private void handleSuccessfulLogin(Client client, Response response) throws MapperException {

            String token = clientAuthenticationService.generateClientToken(client);
            OffsetDateTime expirationDate = clientAuthenticationService.getExpirationDate();

            LoginResponse loginResponse = LoginResponse.builder()
                    .token(token)
                    .expirationDate(expirationDate)
                    .user(LoginResponse.UserDetails.builder()
                            .id(client.getId())
                            .email(client.getEmail())
                            .name(client.getName())
                            .roles(client.getRoles())
                            .build())
                    .build();

            response
                    .setStatusCode(Response.SC_OK)
                    .setHeader("Authorization", String.format("Bearer %s", token))
                    .body(JsonMapper.toJson(loginResponse))
                    .build();

    }

    private void handleUnauthorizedLogin(Response response) {

        response
                .setStatusCode(Response.SC_NOT_AUTHORIZED)
                .error("Unauthorized")
                .message("Invalid email or password")
                .build();

    }

    private void handleInvalidRequestBody(Response response, MapperException e) {

        response
                .setStatusCode(Response.SC_BAD_REQUEST)
                .error("Bad Request")
                .message("Invalid request body")
                .details(e.getMessage())
                .build();

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginResponse {

        private String token;
        private OffsetDateTime expirationDate;
        private UserDetails user;

        @Getter
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class UserDetails {

            private long id;
            private String email;
            private String name;
            private Set<ClientRole> roles;

        }

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class CredentialsDTO {

        private String email;
        private String password;

    }
}