package com.spareparts.store.service.mapper;

import com.spareparts.store.controller.dto.ClientDTO;
import com.spareparts.store.repository.entity.ClientEntity;

public class ClientMapper {

    public static ClientDTO toDTO(ClientEntity entity) {
        return new ClientDTO(entity.getId(), entity.getName(), entity.getEmail());
    }

    public static ClientEntity toEntity(ClientDTO dto) {
        return new ClientEntity(dto.getId(), dto.getName(), dto.getEmail());
    }
}
