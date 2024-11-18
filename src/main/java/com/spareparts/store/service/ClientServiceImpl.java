package com.spareparts.store.service;

import com.spareparts.store.dto.ClientDTO;
import com.spareparts.store.mapper.ClientMapper;
import com.spareparts.store.repository.ClientEntityRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ClientServiceImpl {

    private final ClientEntityRepository clientRepository;

    public ClientServiceImpl(ClientEntityRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll().stream()
                .map(ClientMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ClientDTO getClientById(Long id) {
        return clientRepository.findById(id)
                .map(ClientMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Client not found with ID: " + id));
    }

    public ClientDTO saveClient(ClientDTO clientDTO) {
        var savedEntity = clientRepository.save(ClientMapper.toEntity(clientDTO));
        return ClientMapper.toDTO(savedEntity);
    }
}
