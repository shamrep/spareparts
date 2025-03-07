package com.gymapp.repository;

import com.gymapp.repository.entity.MembershipEntity;
import com.gymapp.repository.entity.MembershipType;
import com.gymapp.repository.util.DatabaseTestManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.Optional;


class MembershipRepositoryTest {

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
        membershipRepository = new MembershipRepository(jdbcClient);
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
//        Assertions.assertThat(foundMembership).
    }

    @Test
    void updateAndFetchMembership() {

        long clientGeneratedId = jdbcClient
                .sql("insert into clients (email, name) values ('client1@gmail.com', 'bot1') returning id;")
                .query(Long.class)
                .single();

        long membershipGeneratedId = jdbcClient.sql("""
                        insert into memberships (client_id, type, start_date, end_date, price)
                        values (:clientId, 'MONTHLY', '2024-11-20T08:00:00Z', '2024-12-20T08:00:00Z', 99.99)
                        returning id;
                        """)
                .param("clientId", clientGeneratedId)
                .query(Long.class)
                .single();

        Optional<MembershipEntity> foundOptionalMembership = membershipRepository.findById(membershipGeneratedId);
        Assertions.assertThat(foundOptionalMembership)
                .as("Membership with ID %s should exist", membershipGeneratedId)
                .isPresent();

        MembershipEntity modifiedMembership = new MembershipEntity(
                membershipGeneratedId,
                clientGeneratedId,
                MembershipType.ANNUAL,
                OffsetDateTime.now(Clock.systemUTC()),
                OffsetDateTime.now(Clock.systemUTC()).plusMonths(12),
                new BigDecimal("299.99")
        );

        membershipRepository.update(modifiedMembership);
        foundOptionalMembership = membershipRepository.findById(membershipGeneratedId);

        Assertions.assertThat(foundOptionalMembership)
                .as("Updated membership with ID %s should exist", membershipGeneratedId)
                .isPresent();
        Assertions.assertThat(foundOptionalMembership.get())
                .as("Updated membership should match the modified membership entity")
                .isEqualTo(modifiedMembership);
    }

    @Test
    void shouldDeleteMembershipById() {
        // Arrange: Insert a client and a membership
        long clientGeneratedId = jdbcClient
                .sql("insert into clients (email, name) values ('client1@gmail.com', 'bot1') returning id;")
                .query(Long.class)
                .single();

        long membershipGeneratedId = jdbcClient.sql("""
                    insert into memberships (client_id, type, start_date, end_date, price)
                    values (:clientId, 'MONTHLY', '2024-11-20T08:00:00Z', '2024-12-20T08:00:00Z', 99.99)
                    returning id;
                    """)
                .param("clientId", clientGeneratedId)
                .query(Long.class)
                .single();

        Optional<MembershipEntity> membership = membershipRepository.findById(membershipGeneratedId);
        Assertions.assertThat(membership).isPresent(); // Ensure it exists before deletion

        // Act: Delete the membership
        membershipRepository.delete(membershipGeneratedId);

        // Assert: Verify that the membership no longer exists
        membership = membershipRepository.findById(membershipGeneratedId);
        Assertions.assertThat(membership).isEmpty(); // Ensure it is deleted
    }
}
