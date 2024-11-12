package com.spareparts.store.repository;

import com.spareparts.store.model.Customer;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@Testcontainers
public class CustomerRepositoryTest {

    private static final String POSTGRES_IMAGE = "postgres:17";
    private static final String DATABASE_NAME = "testdb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";

    //todo get parameters from appl.prop
//    @Container
    static PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>(POSTGRES_IMAGE)
                    .withDatabaseName(DATABASE_NAME)
                    .withUsername(USERNAME)
                    .withPassword(PASSWORD);

    static CustomerRepository customerRepository;

    static DataSource dataSource;

    @BeforeAll
    static void setUp() {
        // Start the container and configure the connection manager
        postgreSQLContainer.start();

        DatabaseConnectionManager.setUrl(postgreSQLContainer.getJdbcUrl());
        DatabaseConnectionManager.setUsername(postgreSQLContainer.getUsername());
        DatabaseConnectionManager.setPassword(postgreSQLContainer.getPassword());

        Map<String, Object> args = new HashMap<>();
        args.put("url", postgreSQLContainer.getJdbcUrl());
        args.put("username", postgreSQLContainer.getUsername());
        args.put("password", postgreSQLContainer.getPassword());
        args.put("changeLogFile", "db/changelog/db.changelog-master.xml");

        LiquibaseRunner.runLiquibaseMigrations(args);

        // Initialize the repository
        customerRepository = new CustomerRepository();

        if (dataSource == null) {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(postgreSQLContainer.getJdbcUrl());
            config.setUsername(postgreSQLContainer.getUsername());
            config.setPassword(postgreSQLContainer.getPassword());
            config.setDriverClassName(postgreSQLContainer.getDriverClassName());

            dataSource = new HikariDataSource(config);
        }
    }

    @AfterAll
    public static void tearDown() {
        postgreSQLContainer.stop();
    }

    @Test
    void testGetCustomers() {
        // insert test data
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("INSERT INTO customers (email) VALUES ('test1@gmail.com')");
            statement.execute("INSERT INTO customers (email) VALUES ('test2@gmail.com')");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to set up test data", e);
        }

        List<Customer> customers = customerRepository.get();

        assertEquals(2, customers.size());
        assertEquals("test1@gmail.com", customers.get(0).getEmail());
        assertEquals("test2@gmail.com", customers.get(1).getEmail());
    }

}
