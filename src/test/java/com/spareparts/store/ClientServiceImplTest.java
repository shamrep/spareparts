package com.spareparts.store;

import com.spareparts.store.mapper.ClientMapper;
import com.spareparts.store.repository.ClientRepository;
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

    @BeforeEach
    void setup() {
        clientRepository = mock(ClientRepository.class);
        clientMapper = mock(ClientMapper.class);
        clientService = new ClientServiceImpl(clientRepository, clientMapper);
    }

    @Test
    void shouldRegisterClientSuccessfully() throws Exception {
        // Arrange
        Client inputClient = new Client(null, "client1@gmail.com", "Client Name", "password123");
        Client hashedClient = new Client(null, "client1@gmail.com", "Client Name", "hashedPassword");
        ClientEntity clientEntity = new ClientEntity(1L, "client1@gmail.com", "Client Name", "hashedPassword");

        when(clientRepository.existsByEmail(inputClient.getEmail())).thenReturn(false);
        when(clientMapper.toClientEntity(hashedClient)).thenReturn(clientEntity);
        when(clientRepository.save(clientEntity)).thenReturn(1L);
        when(clientRepository.findById(1L)).thenReturn(Optional.of(clientEntity));
        when(clientMapper.toClient(clientEntity)).thenReturn(hashedClient);

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
        Client duplicateClient = new Client(null, "duplicate@gmail.com", "Client Name", "password123");

        when(clientRepository.existsByEmail(duplicateClient.getEmail())).thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> clientService.registerClient(duplicateClient))
                .isInstanceOf(EmailAlreadyInUseException.class)
                .hasMessage("Email is already registered");

        verify(clientRepository).existsByEmail(duplicateClient.getEmail());
        verifyNoMoreInteractions(clientRepository);
    }
}
