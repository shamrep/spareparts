package com.spareparts.store.service;

import com.spareparts.store.service.model.Client;
import com.spareparts.store.repository.ClientRepository;

import java.util.List;

public class ClientServiceImpl {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getAllClients() {
//        return clientRepository.findAll().stream()
//                .map(ClientMapper::toDTO)
//                .collect(Collectors.toList());
        return  null;
    }

    public Client getClientById(Long id) {
//        return clientRepository.findById(id)
//                .map(ClientMapper::toDTO)
//                .orElseThrow(() -> new RuntimeException("Client not found with ID: " + id));
        return null;
    }

    public Client saveClient(Client clientDTO) {
//        var savedEntity = clientRepository.save(ClientMapper.toEntity(clientDTO));
//        return ClientMapper.toDTO(savedEntity);
        return null;
    }
}
