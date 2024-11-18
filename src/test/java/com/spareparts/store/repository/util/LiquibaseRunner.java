package com.spareparts.store.repository.util;

import liquibase.command.CommandScope;
import liquibase.exception.LiquibaseException;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.HashMap;
import java.util.Map;

public class LiquibaseRunner {

    public static void runLiquibaseMigrations(Map<String, Object> scopeArgs) {

        try {
            CommandScope updateScope = new CommandScope("update");
            scopeArgs.forEach(updateScope::addArgumentValue);
            updateScope.execute();

            System.out.println("Liquibase migrations applied successfully.");

        } catch (LiquibaseException e) {
            throw new RuntimeException("Failed to run Liquibase update command", e);
        }
    }

    public static void runLiquibaseMigrations(PostgreSQLContainer<?> container) {

        try {
            Map<String, Object> scopeArgs = new HashMap<>();
            scopeArgs.put("url", container.getJdbcUrl());
            scopeArgs.put("username", container.getUsername());
            scopeArgs.put("password", container.getPassword());
            scopeArgs.put("changeLogFile", "db/changelog/db.changelog-master.xml");

            CommandScope updateScope = new CommandScope("update");
            scopeArgs.forEach(updateScope::addArgumentValue);
            updateScope.execute();

            System.out.println("Liquibase migrations applied successfully.");

        } catch (Exception e) {
            throw new RuntimeException("Failed to run Liquibase update command", e);
        }

    }

}
