package com.gymapp.repository;

import com.spareparts.store.repository.entity.PermissionEntity;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.Optional;

@AllArgsConstructor
public class PermissionRepository {

    private JdbcClient jdbcClient;

    public Optional<PermissionEntity> findById(long id) {

        return jdbcClient
                .sql("select * from permissions where id = :permissionId")
                .param("permissionId", id)
                .query(PermissionEntity.class).optional();
    }

    public long save(PermissionEntity permissionEntity) {

        return jdbcClient
                .sql("insert into permissions (id, name, creation_date) values (:permissionId, :name, :creationDate) returning id")
                .param("permissionId", permissionEntity.getId())
                .param("name", permissionEntity.getName())
                .param("creationDate", permissionEntity.getCreationDate())
                .query(Long.class)
                .single();
    }

}
