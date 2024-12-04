package com.spareparts.store.mapper;

import com.spareparts.store.controller.dto.ClientDTO;
import com.spareparts.store.repository.entity.ClientEntity;
import com.spareparts.store.service.model.Client;

public class ClientMapperImpl implements ClientMapper {
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
        return null;
    }
}
