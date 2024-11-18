package com.spareparts.store.repository.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;

public class DataSourceManager {

    private static DataSource dataSource;

    public static DataSource getDataSource(PostgreSQLContainer<?> postgreSQLContainer) {

        if (dataSource == null) {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(postgreSQLContainer.getJdbcUrl());
            config.setUsername(postgreSQLContainer.getUsername());
            config.setPassword(postgreSQLContainer.getPassword());
            config.setDriverClassName(postgreSQLContainer.getDriverClassName());

            dataSource = new HikariDataSource(config);
        }

        return dataSource;
    }
}
