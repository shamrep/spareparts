package com.spareparts.store.controller;

import com.spareparts.store.controller.dto.ClientDTO;
import com.spareparts.store.mapper.ClientMapper;
import com.spareparts.store.service.ClientService;
import com.spareparts.store.service.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @Autowired
    public ClientController(ClientService clientService, ClientMapper clientMapper) {
        this.clientService = clientService;
        this.clientMapper = clientMapper;
    }

    @PostMapping
    public ResponseEntity<?> createClient(@RequestBody ClientDTO clientDTO) {

        Client client = clientMapper.toClient(clientDTO);
//        Client createdClient = clientService.registerClient(client);
//
//        ClientDTO responseDTO = clientMapper.toClientDTO(createdClient);

//        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);

        return null;
    }
}
