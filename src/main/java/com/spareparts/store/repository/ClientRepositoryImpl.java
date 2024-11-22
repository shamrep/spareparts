package com.spareparts.store.repository;

import com.spareparts.store.repository.entity.ClientEntity;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class ClientRepositoryImpl implements ClientRepository {

    private JdbcClient jdbcClient;

    @Override
    public Optional<ClientEntity> findById(Long id) {

        return jdbcClient
                .sql("select * from clients where id = ?;")
                .param(id)
                .query(ClientEntity.class)
                .optional();
    }

    @Override
    public List<ClientEntity> findAll() {
        return List.of();
    }

    @Override
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

    @Override
    public void update(ClientEntity clientEntity) {

    }

    @Override
    public void delete(Long id) {

    }

}

