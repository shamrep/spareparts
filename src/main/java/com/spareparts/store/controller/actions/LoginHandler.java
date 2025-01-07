package com.spareparts.store.controller.actions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spareparts.store.controller.Request;
import com.spareparts.store.controller.Response;
import com.spareparts.store.service.ClientService;
import com.spareparts.store.service.ClientServiceImpl;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class LoginHandler implements Handler {

    private final ClientService clientService;

    public LoginHandler() {
        this.clientService = new ClientServiceImpl();
    }

    @Override
    public void handle(Request request, Response response) {
        ObjectMapper objectMapper = new ObjectMapper();

//        Map<String, Object> jsonMap = objectMapper.readValue(request.getBody(), Map.class);






        Map<String, Object> claims = new HashMap<>();
//        claims.put("email", email);


//        Optional<Client> client = clientService.validateCredentials(email, password);

//        if (client.isPresent()) {
//            String token = JwtUtils.generateToken(claims);
//            String json = Mapper.toJson(new LoginResponse(email, token));
//
//            response
//                    .setStatus(Response.SC_OK)
//                    .setContentType("application/json")
//                    .jsonBody(json);
//        } else {
//            //todo put in
//            Map<String, Object> errorResponse = new HashMap<>();
//            errorResponse.put("timestamp", Instant.now().toString());
////            errorResponse.put("status", HttpStatus.UNAUTHORIZED.value());
//            errorResponse.put("error", "Unauthorized");
//            errorResponse.put("message", "Invalid username or password.");
//            errorResponse.put("path", "/api/auth/login");
//
//            response.setContentType("application/json")
//                    .setStatus(Response.SC_NOT_AUTHORIZED)
//                    .jsonBody(Mapper.toJson(errorResponse));
//        }
    }

    @Getter
    private static class LoginResponse {
        private String email;
        private String token;

        public LoginResponse(String email, String token) {
            this.email = email;
            this.token = token;
        }
    }
}
