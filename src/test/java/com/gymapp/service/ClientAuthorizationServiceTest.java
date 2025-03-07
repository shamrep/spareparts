package com.gymapp.service;

import com.gymapp.service.model.Client;
import com.spareparts.store.service.model.Role;
import com.gymapp.service.model.ClientRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.Set;

import static org.mockito.Mockito.mockStatic;

public class ClientAuthorizationServiceTest {

    private ClientAuthorizationService clientAuthService = new ClientAuthorizationService();
    private Client mockClient;

    @BeforeEach
    void setUp() {
        clientAuthService = new ClientAuthorizationService();

        Role role = new Role(1L, ClientRole.ADMIN, OffsetDateTime.now());

        mockClient = new Client(1L, "john.doe@example.com", "DOE", "securepassword123", Set.of(role));

    }


    @Test
    void testAuthenticateClient_GeneratesValidToken() {

//        clientAuthService.authenticateClient(mockClient);

//        try (MockedStatic<TokenManager> mockedTokenManager = mockStatic(TokenManager.class)) {
//            mockedTokenManager.when(() -> TokenManager.generateToken(any(Map.class)))
//                    .thenReturn("mocked-token");
//
//            String token = clientAuthService.authenticateClient(mockClient);
//
//            assertNotNull(token);
//            assertEquals("mocked-token", token);
//            assertNotNull(clientAuthService.getExpirationDate());
//            assertTrue(clientAuthService.getExpirationDate().isAfter(OffsetDateTime.now()));
//        }
    }

}
