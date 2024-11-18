package com.spareparts.store.service;

public interface ClientService {
    Client registerClient(Client client);
    List<Client> getAllClients();
    Optional<Client> getClientById(Long clientId);
    void deleteClient(Long clientId);
}
