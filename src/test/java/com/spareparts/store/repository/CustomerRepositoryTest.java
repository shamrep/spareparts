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
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
public class CustomerRepositoryTest {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:17")
                    .withDatabaseName("testdb")
                    .withUsername("root")
                    .withPassword("1234");

    private static CustomerRepository customerRepository;

    @BeforeAll
    public static void setUp() throws SQLException {
        // Start the container and configure the connection manager
        postgreSQLContainer.start();
        DatabaseConnectionManager.setUrl(postgreSQLContainer.getJdbcUrl());
        DatabaseConnectionManager.setUsername(postgreSQLContainer.getUsername());
        DatabaseConnectionManager.setPassword(postgreSQLContainer.getPassword());



        // Create table and insert test data
//        try (Connection connection = DatabaseConnectionManager.getConnection();
//             Statement statement = connection.createStatement()) {
//            statement.execute("CREATE TABLE customers (id SERIAL PRIMARY KEY, email VARCHAR(255));");
//            statement.execute("INSERT INTO customers (email) VALUES ('test1@example.com');");
//            statement.execute("INSERT INTO customers (email) VALUES ('test2@example.com');");
//        }

        try(Connection connection = DatabaseConnectionManager.getConnection()) {
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
    public void testGetCustomers() {
        List<Customer> customers = customerRepository.get();
        assertEquals(2, customers.size());
        assertEquals("test1@gmail.com", customers.get(0).getEmail());
        assertEquals("test2@gmail.com", customers.get(1).getEmail());
    }


}
