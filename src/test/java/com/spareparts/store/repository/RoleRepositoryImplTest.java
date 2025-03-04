package com.spareparts.store.repository;

import com.spareparts.store.repository.entity.RoleEntity;
import com.spareparts.store.repository.util.DatabaseTestManager;
import com.spareparts.store.service.model.RoleEnum;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

public class RoleRepositoryImplTest {
    private static DatabaseTestManager databaseTestManager;
    private static RoleRepository roleRepository;
    private static JdbcClient jdbcClient;

    @BeforeAll
    static void setUp() {

        databaseTestManager = new DatabaseTestManager();
        databaseTestManager.startContainer();
        databaseTestManager.runLiquibaseMigration();
        databaseTestManager.setConnectionAutoCommit(false);

        jdbcClient = databaseTestManager.getJdbcClient();
        roleRepository = new RoleRepository(jdbcClient);
    }

    @AfterAll
    static void tearDown() {

        databaseTestManager.stopContainer();
    }

    @AfterEach
    void rollbackTransaction() {

        databaseTestManager.rollbackTransaction();
    }

    @Test
    void saveAndThenFindRoleByName() {

//        long clientGeneratedId = jdbcClient
//                .sql("insert into clients (email, name, password) values ('client1@gmail.com', 'bot1', 'password') returning id;")
//                .query(Long.class)
//                .single();

        RoleEntity roleToSave = new RoleEntity(null, RoleEnum.ADMIN, OffsetDateTime.now(ZoneOffset.UTC));

        long generatedRoleId = roleRepository.save(roleToSave);

        Optional<RoleEntity> savedRole = roleRepository.findById(generatedRoleId);

        Assertions.assertThat(savedRole).isPresent();

        RoleEntity expectedRole = new RoleEntity(generatedRoleId, roleToSave.getName(), roleToSave.getCreatedAt());

        boolean b = expectedRole.equals(savedRole.get());

        Assertions.assertThat(savedRole.get()).isEqualTo(expectedRole);
    }

}
