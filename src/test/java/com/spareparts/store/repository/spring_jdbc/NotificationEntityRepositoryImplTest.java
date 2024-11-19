package com.spareparts.store.repository.spring_jdbc;

import com.spareparts.store.repository.entity.ClientEntity;
import com.spareparts.store.repository.entity.NotificationEntity;
import com.spareparts.store.repository.util.ContainerManager;
import com.spareparts.store.repository.util.DataSourceManager;
import com.spareparts.store.repository.util.JdbcTemplateManager;
import com.spareparts.store.repository.util.LiquibaseRunner;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class NotificationEntityRepositoryImplTest {

    static PostgreSQLContainer<?> testPostgreSQLContainer;
    static NotificationEntityRepository testNotificationEntityRepository;
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

        testNotificationEntityRepository = new NotificationEntityRepositoryImpl(testJdbcTemplate);

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
                "insert into clients (email, name) values ('client1@gmail.com', 'bot1');" +
                "insert into clients (email, name) values ('client2@gmail.com', 'bot2');");

        Optional<ClientEntity> client1 = testJdbcTemplate.
                query("select * from clients where email = 'client1@gmail.com';",
                        (rs, rowNum) -> new ClientEntity(
                                rs.getLong("id"),
                                rs.getString("email"),
                                rs.getString("name"))).stream().findFirst();

        NotificationEntity notificationEntity1 = new NotificationEntity(
                null,
                client1.get().getId(),
                "test message for client 1",
                OffsetDateTime.now(ZoneOffset.UTC),
                false);

        long notification1Id = testNotificationEntityRepository.save(notificationEntity1);

        Optional<NotificationEntity> foundNotification1 = testNotificationEntityRepository.findById(notification1Id);

        assertTrue(foundNotification1.isPresent(), "Notification should be found by ID.");

        assertEquals(notification1Id, foundNotification1.get().getId(), "Notification IDs should match.");
        assertEquals(client1.get().getId(), foundNotification1.get().getClientId(), "Client IDs should match.");
        assertEquals("test message for client 1", foundNotification1.get().getMessage(), "Messages should match.");
        assertEquals(notificationEntity1.getSendDate(), foundNotification1.get().getSendDate(), "Dates should match.");
        assertFalse(foundNotification1.get().isRead(), "Value should be false.");

    }
}