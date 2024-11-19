package com.spareparts.store.repository;

import com.spareparts.store.repository.entity.ClientEntity;

import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    Optional<ClientEntity> findById(Long id);
    List<ClientEntity> findAll();
    ClientEntity save(ClientEntity clientEntity);
    void update(ClientEntity clientEntity);
    void delete(Long id);
}
