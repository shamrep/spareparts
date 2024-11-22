package com.spareparts.store.service;

import com.spareparts.store.mapper.ClientMapper;
import com.spareparts.store.repository.ClientRepository;
import com.spareparts.store.service.model.Client;
import com.spareparts.store.service.util.PasswordUtil;

import java.util.List;
import java.util.Optional;

public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper) {

        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    @Override
    public Optional<Client> registerClient(Client client) {


        clientRepository.save(clientMapper.toClientEntity(client));


        return null;
    }

    @Override
    public List<Client> getAllClients() {
        return List.of();
    }

    @Override
    public Optional<Client> getClientById(Long clientId) {
        return Optional.empty();
    }

    @Override
    public void deleteClient(Long clientId) {

    }
}
