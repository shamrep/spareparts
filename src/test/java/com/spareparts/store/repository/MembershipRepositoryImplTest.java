package com.spareparts.store.repository;

import com.spareparts.store.repository.util.ContainerManager;
import com.spareparts.store.repository.util.DatabaseTestManager;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.PostgreSQLContainer;


class MembershipRepositoryImplTest {

    private static PostgreSQLContainer<?> testPostgresSQLContainer;
    private static DatabaseTestManager databaseTestManager;


    @BeforeAll
    static void setUp() {

        testPostgresSQLContainer = ContainerManager.getContainer();
        testPostgresSQLContainer.start();

        databaseTestManager = new DatabaseTestManager();
        databaseTestManager.runLiquibaseMigration();
        databaseTestManager.setConnectionAutoCommit(false);

//        LiquibaseRunner.runLiquibaseMigrations(testPostgresSQLContainer);


    }

}
