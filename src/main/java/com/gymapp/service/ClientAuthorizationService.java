package com.gymapp.service;

import com.gymapp.service.model.ClientRole;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientAuthorizationService {

    private final ClientService clientService;
    private final Map<String, List<ClientRole>> map = new HashMap<>();

    public ClientAuthorizationService() {

        clientService = new ClientService();
        map.put("/admin/*", List.of(ClientRole.ADMIN));
        map.put("/client/*", List.of(ClientRole.OWNER));
        map.put("/stats/attendance", List.of(ClientRole.OWNER, ClientRole.TRAINER));

    }

    public boolean isClientAuthorized(String path, List<ClientRole> clientRoles) {

        return map.get(path).stream().anyMatch(clientRoles::contains);

    }

    public boolean isProtected(String url) {

        return map.containsKey(url);

    }

}
