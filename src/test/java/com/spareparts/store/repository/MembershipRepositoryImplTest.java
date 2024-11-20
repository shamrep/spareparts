package com.spareparts.store.repository;

import com.spareparts.store.repository.entity.MembershipEntity;
import com.spareparts.store.repository.entity.MembershipType;
import com.spareparts.store.repository.util.DatabaseTestManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;


class MembershipRepositoryImplTest {

    private static DatabaseTestManager databaseTestManager;
    private static JdbcClient jdbcClient;
    private static MembershipRepository membershipRepository;

    @BeforeAll
    static void setUp() {

        databaseTestManager = new DatabaseTestManager();
        databaseTestManager.startContainer();
        databaseTestManager.runLiquibaseMigration();
        databaseTestManager.setConnectionAutoCommit(false);
        jdbcClient = databaseTestManager.getJdbcClient();
        membershipRepository = new MembershipRepositoryImpl(jdbcClient);
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
    void saveAndThenFindMembershipById() {

        long client1GeneratedId = jdbcClient
                .sql("insert into clients (email, name) values ('client1@gmail.com', 'bot1') returning id;")
                .query(Long.class)
                .single();

        long client2GeneratedId = jdbcClient
                .sql("insert into clients (email, name) values (?, ?) returning id;")
                .param(1, "client2@gmail.com")
                .param(2, "bot2")
                .query(Long.class)
                .single();

        MembershipEntity membership = new MembershipEntity(
                null,
                client1GeneratedId,
                MembershipType.MONTHLY,
                OffsetDateTime.now(),
                OffsetDateTime.now().plusMonths(1),
                new BigDecimal("99.9"));

        long generatedMembershipId = membershipRepository.save(membership);

        Optional<MembershipEntity> foundMembership = membershipRepository.findById(generatedMembershipId);

        Assertions.assertThat(foundMembership).isPresent();

    }
}
