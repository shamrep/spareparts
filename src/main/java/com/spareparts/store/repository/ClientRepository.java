package com.spareparts.store.repository;

import com.spareparts.store.model.Client;
import com.spareparts.store.model.Trainer;

import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    Optional<Client> findById(Long id);
    List<Client> findAll();
    void save(Client client);
    void update(Client client);
    void delete(Long id);
}
