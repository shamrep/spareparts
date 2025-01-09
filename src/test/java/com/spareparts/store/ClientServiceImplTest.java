package com.spareparts.store;

import com.spareparts.store.mapper.ClientMapper;
import com.spareparts.store.mapper.ClientMapperImpl;
import com.spareparts.store.repository.ClientRepository;
import com.spareparts.store.repository.entity.ClientEntity;
import com.spareparts.store.service.ClientService;
import com.spareparts.store.service.ClientServiceImpl;
import com.spareparts.store.service.RoleServiceImpl;
import com.spareparts.store.service.model.Client;
import com.spareparts.store.service.model.Permission;
import com.spareparts.store.service.model.Role;
import com.spareparts.store.service.util.PasswordUtil;
import com.spareparts.store.service.util.validation.core.validators.BasicValidator;
import com.spareparts.store.service.util.validation.exceptions.EmailAlreadyInUseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;


public class ClientServiceImplTest {

    private ClientService clientService;
    private ClientRepository clientRepository;
    private final ClientMapper clientMapper = new ClientMapperImpl();
    private RoleServiceImpl roleService;
    private BasicValidator validator;

    @BeforeEach
    void setup() {

        clientRepository = mock(ClientRepository.class);
        roleService = mock(RoleServiceImpl.class);
        validator = mock(BasicValidator.class);
        clientService = new ClientServiceImpl(clientMapper, clientRepository, roleService, validator);

    }

    @Test
    void testRegistration() {

        Client inputClient = new Client(null, "client1@gmail.com", "Client Name", "password123", null);
        String hashPassword = PasswordUtil.hashPassword("password123");
        Role defaultRole = new Role(1L, "client", OffsetDateTime.now(), Set.of(new Permission(1L, "buyMembership")));
        ClientEntity clientEntity = new ClientEntity(1L, "client1@gmail.com", "Client Name", hashPassword);
        Client hashedClient = new Client(1L, "client1@gmail.com", "Client Name", hashPassword, Set.of(defaultRole));


        when(clientRepository.existsByEmail(anyString())).thenReturn(false);
        when(roleService.getDefaultRole()).thenReturn(defaultRole);
        when(clientRepository.save(any(ClientEntity.class))).thenReturn(1L);
        when(clientRepository.findById(1L)).thenReturn(Optional.of(clientEntity));

        // Act
        Optional<Client> result = clientService.registerClient(inputClient);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(hashedClient);

        verify(clientRepository).existsByEmail(inputClient.getEmail());
        verify(clientRepository).save(clientEntity);
        verify(clientRepository).findById(1L);
    }

    @Test
    void shouldRegisterClientSuccessfully() {
        // Arrange
        Client inputClient = new Client(null, "client1@gmail.com", "Client Name", "password123", null);
        Client hashedClient = new Client(null, "client1@gmail.com", "Client Name", "hashedPassword", null);
        ClientEntity clientEntity = new ClientEntity(1L, "client1@gmail.com", "Client Name", "hashedPassword");

        when(clientRepository.existsByEmail(anyString())).thenReturn(false);
        when(clientMapper.toNewClientEntity(any(Client.class))).thenReturn(clientEntity);
        when(clientRepository.save(any(ClientEntity.class))).thenReturn(1L);
        when(clientRepository.findById(1L)).thenReturn(Optional.of(clientEntity));
        when(clientMapper.toClient(any(ClientEntity.class))).thenReturn(hashedClient);

        // Act
        Optional<Client> result = clientService.registerClient(inputClient);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(hashedClient);

        verify(clientRepository).existsByEmail(inputClient.getEmail());
        verify(clientRepository).save(clientEntity);
        verify(clientRepository).findById(1L);
    }

    @Test
    void shouldThrowEmailAlreadyInUseExceptionForDuplicateEmail() {
        // Arrange
        Client duplicateClient = new Client(null, "duplicate@gmail.com", "Client Name", "password123", null);

        when(clientRepository.existsByEmail(duplicateClient.getEmail())).thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> clientService.registerClient(duplicateClient))
                .as("Should throw EmailAlreadyInUseException when email is already registered")
                .isInstanceOf(EmailAlreadyInUseException.class)
                .hasMessage("Email is already registered");

        // Verify repository interactions
        verify(clientRepository).existsByEmail(duplicateClient.getEmail());
        verifyNoMoreInteractions(clientRepository);
    }


}
