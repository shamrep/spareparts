package com.spareparts.store.service;

import com.spareparts.store.mapper.ClientMapper;
import com.spareparts.store.mapper.ClientMapperImpl;
import com.spareparts.store.repository.ClientRepository;
import com.spareparts.store.repository.ClientRepositoryImpl;
import com.spareparts.store.repository.entity.ClientEntity;
import com.spareparts.store.service.model.Client;
import com.spareparts.store.service.model.Role;
import com.spareparts.store.service.util.PasswordUtil;
import com.spareparts.store.service.util.validation.core.validators.BasicValidator;
import com.spareparts.store.service.util.validation.exceptions.EmailAlreadyInUseException;
import com.spareparts.store.service.util.validation.exceptions.ValidationException;
import lombok.AllArgsConstructor;

import java.util.*;

@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientMapper clientMapper;
    private ClientRepository clientRepository;
    private RoleServiceImpl roleService;
    private BasicValidator validator;

    public ClientServiceImpl() {

        this.clientMapper = new ClientMapperImpl();
        this.clientRepository = new ClientRepositoryImpl();
        this.roleService = new RoleServiceImpl();
        this.validator = new BasicValidator();

    }

    public ClientServiceImpl(BasicValidator validator, RoleServiceImpl roleService, ClientRepository clientRepository, ClientMapper clientMapper) {

        this.validator = validator;
        this.roleService = roleService;
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;

    }

    @Override
    public Optional<Client> registerClient(Client client) {

        String hashedPassword = PasswordUtil.hashPassword(client.getPassword());

        if (clientRepository.existsByEmail(client.getEmail())) {

            throw new EmailAlreadyInUseException(client.getEmail());

        }

        validateClientCredentials(client);

        Set<Role> roles = new HashSet<>();
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

    @Override
    public List<Client> getAllClients() {

        return List.of(new Client(1L, "test", "bot", "1234", null),
                new Client(2L, "test", "bot", "1234", null));

    }

    @Override
    public Optional<Client> getClientById(Long clientId) {

        Optional<ClientEntity> clientEntity = clientRepository.findById(clientId);

        return clientEntity.map(entity -> clientMapper.toClient(entity, roleService.getClientRoles(clientId)));

    }

    @Override
    public void deleteClient(Long clientId) {

    }


    @Override
    public Optional<Client> loginClient(String email, String password) {

        Optional<Client> client = findClientByEmail(email);
        String hashPassword = PasswordUtil.hashPassword(password);

        return client.filter(c -> c.getPassword().equals(hashPassword));

    }

    @Override
    public Optional<Client> findClientByEmail(String email) {

        return clientRepository
                .findByEmail(email)
                .map(entity -> clientMapper.toClient(entity, roleService.getClientRoles(entity.getId())));

    }

    private boolean hasPermission(Client client, String requiredPermission) {

        return client.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .anyMatch(permission -> permission.getName().equals(requiredPermission));

    }
}
