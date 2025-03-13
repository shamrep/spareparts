package com.gymapp.repository;

import com.gymapp.repository.entity.ClientEntity;
import com.gymapp.repository.entity.RoleEntity;
import com.gymapp.service.model.ClientRole;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ClientRepository {

    private final JdbcClient jdbcClient;

    public Optional<ClientEntity> findById(Long id) {

       return   jdbcClient
                .sql("select * from clients where id = ? join client_roles on clients.id = client_roles.client_id;")
                .param(id)
                .query(ClientEntity.class)
                .optional();
    }

    public List<ClientEntity> findAll() {
        return List.of();
    }

@Transactional
    public void saveClient(ClientEntity clientEntity) {

        // Step 1: Insert client and get generated ID
        Long clientId = jdbcClient.sql("""
                        insert into clients (name, email, password) 
                        values (:name, :email, :password) RETURNING id;
                        """)
                .param("name", clientEntity.getName())
                .param("email", clientEntity.getEmail())
                .param("password", clientEntity.getPassword())
                .query(Long.class)
                .single();

        // Step 2: Insert roles using batch update
        for (ClientRole role : clientEntity.getRoles()) {
            jdbcClient.sql("""
                            insert into clients_roles (client_id, role_id) 
                            values (:clientId, (select id from roles WHERE name = :roleName));
                            """)
                    .param("clientId", clientId)
                    .param("roleName", role.name())
                    .update();
        }
    }

    public long save(ClientEntity clientEntity) {

        return jdbcClient
                .sql("""
                        insert into clients(name, email, password) 
                        values (:name, :email, :password) 
                        returning id;
                        """)
                .param("name", clientEntity.getName())
                .param("email", clientEntity.getEmail())
                .param("password", clientEntity.getPassword())
                .query(Long.class)
                .single();

    }

    public boolean existsByEmail(String email) {

        return jdbcClient
                .sql("select count(*) > 0 from clients where email = :email")
                .param("email", email)
                .query(Boolean.class)
                .single();

    }

    public Optional<ClientEntity> findByEmail(String email) {

        return jdbcClient
                .sql("select from clients where email = :email")
                .param("email", email)
                .query(ClientEntity.class)
                .optional();

    }

}

