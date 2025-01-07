package com.spareparts.store.controller.actions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spareparts.store.controller.Request;
import com.spareparts.store.controller.Response;
import com.spareparts.store.controller.dto.ClientDTO;
import com.spareparts.store.mapper.ClientMapper;
import com.spareparts.store.mapper.ClientMapperImpl;
import com.spareparts.store.service.ClientService;
import com.spareparts.store.service.ClientServiceImpl;

import java.util.List;

public class GetAllClientsHandler implements Handler {

    private final ClientService clientService;
    private final ClientMapper clientMapper;

    public GetAllClientsHandler() {
        this.clientService = new ClientServiceImpl();
        this.clientMapper = new ClientMapperImpl();
    }

    @Override
    public void handle(Request request, Response response) {

        List<ClientDTO> clientDTOS = clientService
                .getAllClients()
                .stream()
                .map(clientMapper::toClientDTO)
                .toList();

        try {
            String jsons = new ObjectMapper().writeValueAsString(clientDTOS);
            response.setStatusCode(200).body(jsons).build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
