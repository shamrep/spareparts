package com.gymapp.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gymapp.controller.dto.ClientCredentialsDTO;
import com.gymapp.controller.dto.ClientDTO;
import com.gymapp.controller.dto.ClientRegistrationDTO;
import com.gymapp.repository.entity.ClientEntity;
import com.gymapp.service.model.Client;
import com.gymapp.service.model.ClientRole;

import java.util.Set;

public class ClientMapper {

    private final ObjectMapper objectMapper = new ObjectMapper();


    public Client toClient(ClientDTO clientDTO) {
        return null;
    }


    public ClientEntity toNewClientEntity(Client client) {

        return new ClientEntity(null, client.getEmail(), client.getName(), client.getPassword());

    }

    public Client toClient(ClientEntity clientEntity, Set<ClientRole> roles) {

        return new Client(clientEntity.getId(), clientEntity.getEmail(), clientEntity.getName(), clientEntity.getPassword(), roles);

    }

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
