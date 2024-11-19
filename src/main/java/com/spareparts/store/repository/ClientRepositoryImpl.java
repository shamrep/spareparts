package com.spareparts.store.repository;

import com.spareparts.store.repository.entity.ClientEntity;

import java.util.List;
import java.util.Optional;

public class ClientRepositoryImpl implements ClientRepository {

    @Override
    public Optional<ClientEntity> findById(Long id) {

        return Optional.empty();

    }

    public List<ClientEntity> findAll() {
        return null;
    }

    @Override
    public ClientEntity save(ClientEntity clientEntity) {
        return null;
    }

    @Override
    public void update(ClientEntity clientEntity) {

    }

    @Override
    public void delete(Long id) {

    }

}

