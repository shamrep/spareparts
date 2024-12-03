package com.spareparts.store.repository;

import com.spareparts.store.repository.entity.RoleEntity;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.Optional;

@AllArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {

    private JdbcClient jdbcClient;

    @Override
    public Optional<RoleEntity> findById(long id) {

        String selectQuery = """
                    select id, name, creation_date
                    from roles
                    where id = :id
                """;

        return jdbcClient.sql(selectQuery)
                .param("id", id)
                .query(RoleEntity.class)
                .optional();

    }

    @Override
    public long save(RoleEntity roleEntity) {

        String insertQuery = """
                    insert into roles (name, creation_date)
                    values (:name, :creationDate)
                    returning id
                """;


        return jdbcClient.sql(insertQuery)
                .param("name", roleEntity.getName())
                .param("creationDate", roleEntity.getCreationDate())
                .query(Long.class)
                .single();
    }
}
