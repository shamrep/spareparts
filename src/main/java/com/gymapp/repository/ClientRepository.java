package com.gymapp.repository;

import com.gymapp.repository.entity.ClientEntity;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ClientRepository {

    private final JdbcClient jdbcClient;

    public Optional<ClientEntity> findById(Long id) {

        return jdbcClient
                .sql("select * from clients where id = ?;")
                .param(id)
                .query(ClientEntity.class)
                .optional();

    }

    public List<ClientEntity> findAll() {
        return List.of();
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

