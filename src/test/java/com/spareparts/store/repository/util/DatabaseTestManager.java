package com.spareparts.store.repository.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import liquibase.command.CommandScope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DatabaseTestManager {

    private final PostgreSQLContainer<?> postgresSQLContainer;
    private final DataSource dataSource;
    private final Connection connection;

    public DatabaseTestManager(PostgreSQLContainer<?> postgresSQLContainer) {

        this.postgresSQLContainer = postgresSQLContainer;

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(postgresSQLContainer.getJdbcUrl());
        config.setUsername(postgresSQLContainer.getUsername());
        config.setPassword(postgresSQLContainer.getPassword());
        config.setDriverClassName(postgresSQLContainer.getDriverClassName());

        dataSource = new HikariDataSource(config);
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to set up test transaction.", e);
        }
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public Connection getConnection() {
        return connection;
    }

    // is it okay to do?
    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(dataSource);
    }

    public void setConnectionAutoCommit(boolean autoCommit) {
        try {
            connection.setAutoCommit(autoCommit);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to set up test transaction", e);
        }
    }

    public void rollbackTransaction() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to roll back test transaction.", e);
        }
    }

    public void runLiquibaseMigration() {

        try {
            Map<String, Object> scopeArgs = new HashMap<>();
            scopeArgs.put("url", postgresSQLContainer.getJdbcUrl());
            scopeArgs.put("username", postgresSQLContainer.getUsername());
            scopeArgs.put("password", postgresSQLContainer.getPassword());
            scopeArgs.put("changeLogFile", "db/changelog/db.changelog-master.xml");

            CommandScope updateScope = new CommandScope("update");
            scopeArgs.forEach(updateScope::addArgumentValue);
            updateScope.execute();

            System.out.println("Liquibase migrations applied successfully.");

        } catch (Exception e) {
            throw new RuntimeException("Failed to run Liquibase update command.", e);
        }
    }
}
