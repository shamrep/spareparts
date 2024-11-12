package com.spareparts.store.repository;

import liquibase.command.CommandScope;
import liquibase.exception.LiquibaseException;

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

}
