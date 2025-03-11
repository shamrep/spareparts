package com.gymapp.service;

import com.gymapp.mapper.ClientMapper;
import com.gymapp.repository.ClientRepository;
import com.gymapp.repository.entity.ClientEntity;
import com.gymapp.service.model.Client;
import com.gymapp.service.model.ClientRole;
import com.gymapp.service.util.validation.core.validators.BasicValidator;
import com.gymapp.service.util.validation.exceptions.EmailAlreadyInUseException;
import com.gymapp.service.util.validation.exceptions.ValidationException;
import lombok.AllArgsConstructor;

import java.util.*;

@AllArgsConstructor
public class ClientService {

    private final ClientMapper clientMapper;
    private ClientRepository clientRepository;

    public ClientService() {

        this.clientMapper = new ClientMapper();

    }

    public List<Client> getAllClients() {

        return List.of(new Client(1L, "test", "bot", "1234", null),
                new Client(2L, "test", "bot", "1234", null));

    }

    public Optional<Client> getClientById(Long clientId) {

        Optional<ClientEntity> clientEntity = clientRepository.findById(clientId);

        return clientEntity.map(entity -> clientMapper.toClient(entity, roleService.getClientRoles(clientId)));

    }

    public void deleteClient(Long clientId) {

    }


    public Optional<Client> findClientByEmail(String email) {

        return Optional.of(new Client(1L, "john.doe@example.com", "DOE", "securepassword123", Set.of(ClientRole.CLIENT)));

    }

}
