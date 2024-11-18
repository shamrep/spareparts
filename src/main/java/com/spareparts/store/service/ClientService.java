package com.spareparts.store.service;

import com.spareparts.store.dto.ClientDTO;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    ClientDTO registerClient(ClientDTO client);
    List<ClientDTO> getAllClients();
    Optional<ClientDTO> getClientById(Long clientId);
    void deleteClient(Long clientId);
}
