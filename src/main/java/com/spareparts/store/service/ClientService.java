package com.spareparts.store.service;

import com.spareparts.store.model.Client;

import java.util.List;

public interface ClientService {
    List<Client> getAllParts();

    Client getPartById(Long id);

    Client addPart(Client client);

    Client updatePart(Long id, Client client);

    void deletePart(Long id);
}
