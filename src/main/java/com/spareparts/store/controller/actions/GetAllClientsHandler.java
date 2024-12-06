package com.spareparts.store.controller.actions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spareparts.store.controller.dto.ClientDTO;
import com.spareparts.store.mapper.ClientMapper;
import com.spareparts.store.mapper.ClientMapperImpl;
import com.spareparts.store.service.ClientService;
import com.spareparts.store.service.ClientServiceImpl;
import com.spareparts.store.service.model.Client;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class GetAllClientsHandler implements Handler {

    private ClientService clientService;
    private ClientMapper clientMapper;

    public GetAllClientsHandler() {
        this.clientService = new ClientServiceImpl();
        this.clientMapper = new ClientMapperImpl();
    }

    @Override
    public HttpExchange handle(HttpExchange exchange) {

        List<Client> clients = clientService.getAllClients();

        List<ClientDTO> clientDTOS = clients
                .stream()
                .map(client -> clientMapper.toClientDTO(client))
                .toList();





        try {
            String jsons = new ObjectMapper().writeValueAsString(clientDTOS);
            exchange.sendResponseHeaders(200, jsons.getBytes(StandardCharsets.UTF_8).length);
            exchange.getResponseBody().write(jsons.getBytes(StandardCharsets.UTF_8));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        return exchange;
    }
}
