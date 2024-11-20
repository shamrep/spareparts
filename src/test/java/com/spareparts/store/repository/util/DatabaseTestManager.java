package com.spareparts.store.repository.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseTestManager {

    private final PostgreSQLContainer<?> postgresSQLContainer;
    private DataSource dataSource;
    private Connection connection;
    private JdbcTemplate jdbcTemplate;
    private JdbcClient jdbcClient;

    public DatabaseTestManager() {

        postgresSQLContainer = ContainerManager.getContainer();
    }

    public void startContainer() {

        postgresSQLContainer.start();
    }

    public void stopContainer() {

        postgresSQLContainer.stop();
    }

    public DataSource getDataSource() {

        if (dataSource == null) {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(postgresSQLContainer.getJdbcUrl());
            config.setUsername(postgresSQLContainer.getUsername());
            config.setPassword(postgresSQLContainer.getPassword());
            config.setDriverClassName(postgresSQLContainer.getDriverClassName());

            dataSource = new HikariDataSource(config);
        }

        return dataSource;
    }

    public Connection getConnection() {

        if (connection == null) {

            try {
                connection = getDataSource().getConnection();
            } catch (SQLException e) {
                throw new RuntimeException("Failed to set up test transaction.", e);
            }
        }

        return connection;
    }

    public JdbcTemplate getJdbcTemplate() {

        if (jdbcTemplate == null) {
            jdbcTemplate = new JdbcTemplate(getDataSource());
        }

        return jdbcTemplate;
    }

    public JdbcClient getJdbcClient() {

        if (jdbcClient == null) {
            jdbcClient = JdbcClient.create(getDataSource());
        }

        return jdbcClient;
    }

    public void setConnectionAutoCommit(boolean autoCommit) {

        try {
            getConnection().setAutoCommit(autoCommit);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to set up test transaction", e);
        }
    }

    public void rollbackTransaction() {

        try {
            getConnection().rollback();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to roll back test transaction.", e);
        }
    }

    public void runLiquibaseMigration() {

        LiquibaseRunner.runLiquibaseMigrations(postgresSQLContainer);
    }
}
