package com.gymapp.controller.actions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gymapp.controller.Request;
import com.gymapp.controller.Response;
import com.gymapp.controller.dto.ClientDTO;
import com.gymapp.mapper.ClientMapper;
import com.gymapp.service.ClientService;

import java.util.List;

public class GetAllClientsHandler implements Handler {

    private final ClientService clientService;
    private final ClientMapper clientMapper;

    public GetAllClientsHandler() {
        this.clientService = new ClientService();
        this.clientMapper = new ClientMapper();
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
