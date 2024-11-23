package com.spareparts.store.service;

import com.spareparts.store.mapper.ClientMapper;
import com.spareparts.store.repository.ClientRepository;
import com.spareparts.store.repository.entity.ClientEntity;
import com.spareparts.store.service.model.Client;
import com.spareparts.store.service.util.PasswordUtil;
import com.spareparts.store.service.util.validation.EmailValidator;
import com.spareparts.store.service.util.validation.ValidationException;

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


            EmailValidator.validate(client);

        String hashedPassword = PasswordUtil.hashPassword(client.getPassword());

        if (clientRepository.existsByEmail(client.getEmail())) {
            throw new EmailAlreadyInUseException("Email is already registered");  // Custom exception
        }

        ClientEntity clientEntity = clientMapper.toClientEntity(
                new Client(client.getId(), client.getEmail(), client.getName(), hashedPassword)
        );

        long clientGeneratedId = clientRepository.save(clientEntity);

        return clientRepository.findById(clientGeneratedId)
                .map(clientMapper::toClient);
    }

    @Override
    public List<Client> getAllClients() {
        return List.of();
    }

    @Override
    public Optional<Client> getClientById(Long clientId) {
        Optional<ClientEntity> clientEntity =  clientRepository.findById(clientId);
//todo: read about it
        return clientEntity.map(clientMapper::toClient);
    }

    @Override
    public void deleteClient(Long clientId) {

    }
}
