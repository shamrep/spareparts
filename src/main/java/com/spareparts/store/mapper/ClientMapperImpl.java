package com.spareparts.store.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spareparts.store.controller.dto.ClientDTO;
import com.spareparts.store.repository.entity.ClientEntity;
import com.spareparts.store.service.model.Client;
import com.spareparts.store.service.model.ClientCredentials;

public class ClientMapperImpl implements ClientMapper {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Client toClient(ClientDTO clientDTO) {
        return null;
    }

    @Override
    public ClientEntity toClientEntity(Client client) {
        return null;
    }

    @Override
    public Client toClient(ClientEntity clientEntity) {

        return new Client(clientEntity.getId(), clientEntity.getEmail(), clientEntity.getName(), clientEntity.getPassword(), null);
    }

    @Override
    public ClientDTO toClientDTO(Client client) {

        return new ClientDTO(client.getId(), client.getName(), client.getEmail());
    }

    @Override
    public String toJson(ClientDTO clientDto) {
        try {
            return objectMapper.writeValueAsString(clientDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting ClientDto to JSON", e);
        }
    }

    @Override
    public ClientDTO jsonToClientDTO(String clientJson) {
        try {
            return objectMapper.readValue(clientJson, ClientDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting JSON to ClientDto", e);
        }
    }

    public Client toClient(ClientCredentials credentials) {
        return new Client(null, credentials.getEmail(), credentials.getName(), credentials.getPassword(), null);
    }
}
