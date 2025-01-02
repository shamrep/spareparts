package com.spareparts.store;

import com.spareparts.store.mapper.ClientMapper;
import com.spareparts.store.repository.ClientRepository;
import com.spareparts.store.repository.RoleRepository;
import com.spareparts.store.repository.entity.ClientEntity;
import com.spareparts.store.service.ClientService;
import com.spareparts.store.service.ClientServiceImpl;
import com.spareparts.store.service.EmailAlreadyInUseException;
import com.spareparts.store.service.model.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class ClientServiceImplTest {
    private ClientService clientService;
    private ClientRepository clientRepository;
    private ClientMapper clientMapper;
    private RoleRepository roleRepository;

    @BeforeEach
    void setup() {
        clientRepository = mock(ClientRepository.class);
        clientMapper = mock(ClientMapper.class);
        roleRepository = mock(RoleRepository.class);
        clientService = new ClientServiceImpl(clientMapper, clientRepository, roleRepository);
    }

    @Test
    void shouldRegisterClientSuccessfully() {
        // Arrange
        Client inputClient = new Client(null, "client1@gmail.com", "Client Name", "password123", null);
        Client hashedClient = new Client(null, "client1@gmail.com", "Client Name", "hashedPassword", null);
        ClientEntity clientEntity = new ClientEntity(1L, "client1@gmail.com", "Client Name", "hashedPassword");

        when(clientRepository.existsByEmail(anyString())).thenReturn(false);
        when(clientMapper.toClientEntity(any(Client.class))).thenReturn(clientEntity);
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
