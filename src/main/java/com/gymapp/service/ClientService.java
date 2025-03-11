package com.gymapp.service;

import com.gymapp.mapper.ClientMapper;
import com.gymapp.repository.ClientRepository;
import com.gymapp.repository.entity.ClientEntity;
import com.gymapp.service.model.Client;
import com.gymapp.service.model.ClientRole;
import com.gymapp.service.util.validation.core.validators.BasicValidator;
import com.gymapp.service.util.validation.exceptions.EmailAlreadyInUseException;
import com.gymapp.service.util.validation.exceptions.ValidationException;
import lombok.AllArgsConstructor;

import java.util.*;

@AllArgsConstructor
public class ClientService {

    private final ClientMapper clientMapper;
    private ClientRepository clientRepository;
    private RoleService roleService;
    private BasicValidator validator;

    public ClientService() {

        this.clientMapper = new ClientMapper();
//        this.clientRepository = new ClientRepository();
//        this.roleService = new RoleService();
//        this.validator = new BasicValidator();

    }

    public ClientService(BasicValidator validator, RoleService roleService, ClientRepository clientRepository, ClientMapper clientMapper) {

        this.validator = validator;
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;

    }

    public Optional<Client> registerClient(Client client) {

//        String hashedPassword = PasswordUtil.hashPassword(client.getPassword());
        String hashedPassword = client.getPassword();

        if (clientRepository.existsByEmail(client.getEmail())) {

            throw new EmailAlreadyInUseException(client.getEmail());

        }

        validateClientCredentials(client);

        Set<ClientRole> roles = new HashSet<>();
        roles.add(roleService.getDefaultRole());

        ClientEntity clientEntity = clientMapper.toNewClientEntity(

                new Client(
                        null,
                        client.getEmail(),
                        client.getName(),
                        hashedPassword,
                        roles
                )
        );

        long clientGeneratedId = clientRepository.save(clientEntity);

        return clientRepository.findById(clientGeneratedId)
                .map(entity -> clientMapper.toClient(entity, roleService.getClientRoles(clientGeneratedId)));

    }

    private void validateClientCredentials(Client client) {

        Map<String, List<String>> errors = validator.validate(client);

        if (!errors.isEmpty()) {
            throw new ValidationException("Client validation failed.", errors);
        }

    }

    public List<Client> getAllClients() {

        return List.of(new Client(1L, "test", "bot", "1234", null),
                new Client(2L, "test", "bot", "1234", null));

    }

    public Optional<Client> getClientById(Long clientId) {

        Optional<ClientEntity> clientEntity = clientRepository.findById(clientId);

        return clientEntity.map(entity -> clientMapper.toClient(entity, roleService.getClientRoles(clientId)));

    }

    public void deleteClient(Long clientId) {

    }


    public Optional<Client> findClientByEmail(String email) {

//        return clientRepository
//                .findByEmail(email)
//                .map(entity -> clientMapper.toClient(entity, roleService.getClientRoles(entity.getId())));

        return Optional.of(new Client(1L, "john.doe@example.com", "DOE", "securepassword123", Set.of(ClientRole.CLIENT)));

    }

}
