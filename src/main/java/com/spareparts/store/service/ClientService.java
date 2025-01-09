package com.spareparts.store.service;

import com.spareparts.store.service.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    Optional<Client> registerClient(Client client);
    List<Client> getAllClients();
    Optional<Client> getClientById(Long clientId);
    void deleteClient(Long clientId);

    Optional<Client> loginClient(String email, String password);

    Optional<Client> findClientByEmail(String email);
}
