package com.spareparts.store.service;

import com.spareparts.store.model.Client;
import com.spareparts.store.repository.ClientRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepositoryImpl clientRepository;

    @Override
    public List<Client> getAllParts() {
        return clientRepository.findAll();
    }

    @Override
    public Client getPartById(Long id) {
        return clientRepository.getReferenceById(id);
    }

    @Override
    public Client addPart(Client client) {
        return clientRepository.save(client);
    }

    //find method for update
    @Override
    public Client updatePart(Long id, Client client) {
        return null;
    }

    @Override
    public void deletePart(Long id) {
        clientRepository.deleteById(id);
    }
}
