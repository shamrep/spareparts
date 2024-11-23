package com.spareparts.store.mapper;

import com.spareparts.store.controller.dto.ClientDTO;
import com.spareparts.store.repository.entity.ClientEntity;
import com.spareparts.store.service.model.Client;


public interface ClientMapper {
    // Map from ClientDTO (Controller) to Client (Service)
    Client toClient(ClientDTO clientDTO);

    // Map from Client (Service) to ClientEntity (Repository)
    ClientEntity toClientEntity(Client client);

    // Map from ClientEntity (Repository) to Client (Service) if needed
    Client toClient(ClientEntity clientEntity);

    // Optional: Map from Client (Service) to ClientDTO (Controller) if you need to return to the controller
    ClientDTO toClientDTO(Client client);
}
