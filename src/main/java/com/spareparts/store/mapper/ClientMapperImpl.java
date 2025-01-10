package com.spareparts.store.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spareparts.store.controller.dto.ClientCredentialsDTO;
import com.spareparts.store.controller.dto.ClientDTO;
import com.spareparts.store.controller.dto.ClientRegistrationDTO;
import com.spareparts.store.repository.entity.ClientEntity;
import com.spareparts.store.service.model.Client;
import com.spareparts.store.service.model.Role;

import java.util.Set;

public class ClientMapperImpl implements ClientMapper {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Client toClient(ClientDTO clientDTO) {
        return null;
    }

    @Override
    public ClientEntity toNewClientEntity(Client client) {

        return new ClientEntity(null, client.getEmail(), client.getName(), client.getPassword());

    }

    @Override
    public Client toClient(ClientEntity clientEntity, Set<Role> roles) {

        return new Client(clientEntity.getId(), clientEntity.getEmail(), clientEntity.getName(), clientEntity.getPassword(), roles);

    }

    @Override
    public ClientDTO toClientDTO(Client client) {

        return new ClientDTO(client.getId(), client.getName(), client.getEmail());
    }

    public Client toClient(ClientCredentialsDTO credentialsDTO) {

        return new Client(null, credentialsDTO.getEmail(), null, credentialsDTO.getPassword(), null);

    }

    public Client toClient(ClientRegistrationDTO registrationDTO) {

        return new Client(null, registrationDTO.getEmail(), registrationDTO.getName(), registrationDTO.getPassword(), null);

    }
}
