package com.spareparts.store.repository;

import com.spareparts.store.repository.entity.ClientEntity;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class ClientRepository {

    private JdbcClient jdbcClient;

    public ClientRepository() {
//        this.jdbcClient = JdbcClient.create(DataSourceManager.getDataSource());
    }

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

    public void update(ClientEntity clientEntity) {

    }

    public void delete(Long id) {

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

