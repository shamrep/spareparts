package com.spareparts.store.repository;

import com.spareparts.store.model.Client;

import java.util.List;
import java.util.Optional;

public class ClientRepositoryImpl implements ClientRepository {

    @Override
    public Optional<Client> findById(Long id) {

        return Optional.empty();

    }

    public List<Client> findAll() {
        return null;
    }

    @Override
    public void save(Client client) {

    }

    @Override
    public void update(Client client) {

    }

    @Override
    public void delete(Long id) {

    }

}
