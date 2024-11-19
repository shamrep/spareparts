package com.spareparts.store.service;

import com.spareparts.store.dto.ClientDTO;
import com.spareparts.store.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    Client registerClient(Client client);
    List<Client> getAllClients();
    Optional<Client> getClientById(Long clientId);
    void deleteClient(Long clientId);
}
