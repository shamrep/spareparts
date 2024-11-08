package com.spareparts.store.repository;

import com.spareparts.store.model.Customer;
import liquibase.exception.LiquibaseException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
public class CustomerRepositoryTest {
    //todo get parameters from appl.prop
    @Container
    static PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:17")
                    .withDatabaseName("testdb")
                    .withUsername("root")
                    .withPassword("1234");

    private static CustomerRepository customerRepository;

    @BeforeAll
    static void setUp() throws SQLException {
        // Start the container and configure the connection manager
        postgreSQLContainer.start();
        DatabaseConnectionManager.setUrl(postgreSQLContainer.getJdbcUrl());
        DatabaseConnectionManager.setUsername(postgreSQLContainer.getUsername());
        DatabaseConnectionManager.setPassword(postgreSQLContainer.getPassword());

        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            LiquibaseRunner.runLiquibaseMigrations(connection);
        } catch (LiquibaseException e) {
            throw new RuntimeException(e);
        }

        // Initialize the repository
        customerRepository = new CustomerRepository();
    }

    @AfterAll
    public static void tearDown() {
        postgreSQLContainer.stop();
    }

    @Test
    void testGetCustomers() {
        List<Customer> customers = customerRepository.get();
        assertEquals(2, customers.size());
        assertEquals("test1@gmail.com", customers.get(0).getEmail());
        assertEquals("test2@gmail.com", customers.get(1).getEmail());
    }

}
