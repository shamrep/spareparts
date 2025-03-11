package com.gymapp.service;

import com.gymapp.mapper.ClientMapper;
import com.gymapp.repository.ClientRepository;
import com.gymapp.repository.entity.ClientEntity;
import com.gymapp.service.model.Client;
import com.gymapp.service.model.ClientRole;
import com.gymapp.service.util.TokenManager;
import com.gymapp.service.util.validation.core.validators.BasicValidator;
import com.gymapp.service.util.validation.exceptions.EmailAlreadyInUseException;
import com.gymapp.service.util.validation.exceptions.ValidationException;
import io.jsonwebtoken.Claims;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;

@RequiredArgsConstructor
public class ClientAuthenticationService {

    private final long expirationTime = 3600;
    @Getter
    private OffsetDateTime expirationDate;
    private final ClientService clientService;
    private BasicValidator validator;
    private RoleService roleService;
    private ClientRepository clientRepository;
    private ClientMapper clientMapper;

    public ClientAuthenticationService() {
        this(new ClientService());
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

    public String generateClientToken(Client client) {

        Map<String, Object> claims = new HashMap<>();
        expirationDate = OffsetDateTime.now().plusHours(24);

        claims.put("email", client.getEmail());
        claims.put("exp", expirationDate.toEpochSecond());
        claims.put("userId", client.getId());
        claims.put("roles", client.getRoles());

        return TokenManager.generateToken(claims);

    }

    public boolean isTokenValid(String token) {

        if (TokenManager.validateToken(token)) {
            Claims claims = TokenManager.decodeToken(token);

            Long expTimestamp = claims.get("exp", Long.class);

            if (OffsetDateTime.ofInstant(Instant.ofEpochSecond(expTimestamp), ZoneOffset.UTC).isBefore(OffsetDateTime.now())) {
                throw new RuntimeException("Token is expired!");
            }
        }

        return false;

    }

    public Claims extractTokenDetails(String token) {

        return TokenManager.decodeToken(token);

    }

    public List<ClientRole> getClientRoles(String token) {

        return extractTokenDetails(token).get("roles", List.class);

    }

    public boolean isValidToken(String token) {

        return TokenManager.validateToken(token);

    }

    public Optional<Client> authenticateClient(String email, String password) {

        Optional<Client> client = clientService.findClientByEmail(email);
//        String hashPassword = PasswordUtil.hashPassword(password);
        String hashPassword = password;

        return client.filter(c -> c.getPassword().equals(hashPassword));

    }

    private void validateClientCredentials(Client client) {

        Map<String, List<String>> errors = validator.validate(client);

        if (!errors.isEmpty()) {
            throw new ValidationException("Client validation failed.", errors);
        }

    }

}
