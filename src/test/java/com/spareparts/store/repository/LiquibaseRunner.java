package com.spareparts.store.repository;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.SQLException;

public class LiquibaseRunner {

    public static void runLiquibaseMigrations(Connection connection) throws SQLException, LiquibaseException {
        JdbcConnection jdbcConnection = new JdbcConnection(connection);

        Liquibase liquibase = new Liquibase(
                "db/changelog/db.changelog-master.xml", // Path to changelog file
                new ClassLoaderResourceAccessor(),
                jdbcConnection
        );

        try {
            liquibase.update((String) null, String.valueOf(new LabelExpression()));
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to run Liquibase migrations", e);
        }
    }

}
