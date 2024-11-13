package com.spareparts.store.repository;

import org.testcontainers.containers.Container;
import org.testcontainers.containers.PostgreSQLContainer;

public class ContainerManager {
    //todo get parameters from appl.prop
    private static final String POSTGRES_IMAGE = "postgres:17";
    private static final String DATABASE_NAME = "testdb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";

    private static PostgreSQLContainer<?> container;

    public static PostgreSQLContainer<?>  getContainer() {

        if (container == null) {
            container = new PostgreSQLContainer<>(POSTGRES_IMAGE)
                    .withDatabaseName(DATABASE_NAME)
                    .withUsername(USERNAME)
                    .withPassword(PASSWORD);

            return container;
        }

        return container;

    }
}
