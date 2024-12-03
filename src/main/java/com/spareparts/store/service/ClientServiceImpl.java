package com.spareparts.store.service;

import com.spareparts.store.mapper.ClientMapper;
import com.spareparts.store.repository.ClientRepository;
import com.spareparts.store.repository.entity.ClientEntity;
import com.spareparts.store.service.model.Client;
import com.spareparts.store.service.model.Permission;
import com.spareparts.store.service.util.PasswordUtil;

import java.util.HashSet;
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
        System.out.println("Registering client: " + client);

        String hashedPassword = PasswordUtil.hashPassword(client.getPassword());
        System.out.println("Hashed password: " + hashedPassword);

        if (clientRepository.existsByEmail(client.getEmail())) {
            System.out.println("Email already exists: " + client.getEmail());
            throw new EmailAlreadyInUseException("Email is already registered");
        }

        ClientEntity clientEntity = clientMapper.toClientEntity(

                new Client(
                        client.getId(),
                        client.getEmail(),
                        client.getName(),
                        hashedPassword,
                        new HashSet<Permission>().add(new Permission()))
        );
        System.out.println("Mapped to entity: " + clientEntity);

        long clientGeneratedId = clientRepository.save(clientEntity);
        System.out.println("Generated client ID: " + clientGeneratedId);

        return clientRepository.findById(clientGeneratedId)
                .map(clientMapper::toClient);
    }

    @Override
    public List<Client> getAllClients() {
        return List.of();
    }

    @Override
    public Optional<Client> getClientById(Long clientId) {
        Optional<ClientEntity> clientEntity = clientRepository.findById(clientId);
//todo: read about it
        return clientEntity.map(clientMapper::toClient);
    }

    @Override
    public void deleteClient(Long clientId) {

    }
}
