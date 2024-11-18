package com.spareparts.store.repository.spring_jdbc;

import com.spareparts.store.model.Client;
import com.spareparts.store.model.Notification;
import com.spareparts.store.repository.util.ContainerManager;
import com.spareparts.store.repository.util.DataSourceManager;
import com.spareparts.store.repository.util.JdbcTemplateManager;
import com.spareparts.store.repository.util.LiquibaseRunner;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class NotificationRepositoryImplTest {

    static PostgreSQLContainer<?> testPostgreSQLContainer;
    static NotificationRepository testNotificationRepository;
    static DataSource testDataSource;
    static Connection testConnection;
    static JdbcTemplate testJdbcTemplate;

    @BeforeAll
    static void setUp() {

        testPostgreSQLContainer = ContainerManager.getContainer();
        testPostgreSQLContainer.start();

        LiquibaseRunner.runLiquibaseMigrations(testPostgreSQLContainer);

        testDataSource = DataSourceManager.getDataSource(testPostgreSQLContainer);

        testJdbcTemplate = JdbcTemplateManager.getJdbcTemplate(testDataSource);

        testNotificationRepository = new NotificationRepositoryImpl(testJdbcTemplate);

        try {
            testConnection = testDataSource.getConnection();
            testConnection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to set up test transaction", e);
        }
    }

    @AfterAll
    public static void tearDown() {

        try {
            testConnection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to close test connection", e);
        }

        testPostgreSQLContainer.stop();
    }

    @AfterEach
    void rollBackTransaction() {
        try {
            testConnection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to roll back test transaction.", e);
        }
    }

    @Test
    void findByIdTest() {
        testJdbcTemplate.execute(
                "insert into clients (email) values ('client1@gmail.com');" +
                "insert into clients (email) values ('client2@gmail.com');");

        Optional<Client> client1 = testJdbcTemplate.
                query("select * from clients where email = 'client1@gmail.com';",
                        (rs, rowNum) -> new Client(
                                rs.getLong("id"),
                                rs.getString("email"))).stream().findFirst();

        Notification notification1 = new Notification(
                null,
                client1.get().getId(),
                "test message for client 1",
                OffsetDateTime.now(),
                false);

        long notification1Id = testNotificationRepository.save(notification1);

        Optional<Notification> foundNotification1 = testNotificationRepository.findById(notification1Id);

        assertTrue(foundNotification1.isPresent(), "Notification should be found by ID.");

        assertEquals(notification1Id, foundNotification1.get().getId(), "Notification IDs should match.");
        assertEquals(client1.get().getId(), foundNotification1.get().getClientId(), "Client IDs should match.");
        assertEquals("test message for client 1", foundNotification1.get().getMessage(), "Messages should match.");
        assertEquals(notification1.getSendDate(), foundNotification1.get().getSendDate(), "Dates should match.");
        assertFalse(foundNotification1.get().isRead(), "Value should be false.");

    }
}