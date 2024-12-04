package com.spareparts.store.repository;

import com.spareparts.store.repository.entity.PermissionEntity;
import com.spareparts.store.repository.entity.RoleEntity;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {

    private JdbcClient jdbcClient;

    @Override
    public Optional<RoleEntity> findById(long id) {

        String selectQuery = "select * from roles where id = :id";

        return jdbcClient.sql(selectQuery)
                .param("id", id)
                .query(RoleEntity.class)
                .optional();
    }

    @Override
    public long save(RoleEntity roleEntity) {

        String insertQuery = """
                    insert into roles (name, created_at)
                    values (:roleName, :createdAt)
                    returning id
                """;


        return jdbcClient.sql(insertQuery)
                .param("roleName", roleEntity.getName())
                .param("createdAt", roleEntity.getCreatedAt())
//                .param("createrId", roleEntity.getCreatorId())
                .query(Long.class)
                .single();
    }

    @Override
    public Optional<RoleEntity> findByName(String roleName) {

        return jdbcClient
                .sql("""
                select * from roles where name = :roleName
                """)
                .param("roleName", roleName)
                .query(RoleEntity.class)
                .optional();
    }

    @Override
    public List<PermissionEntity> findPermissionsByRoleId(Long roleId) {

        return jdbcClient.sql("""
                select 
                    permissions.id, 
                    name, 
                    permissions.creation_date 
                from 
                    permissions join role_permissions on permission.id = role_permissions.permission_id 
                where role_id = :roleId
                """)
                .param("roleId", roleId)
                .query(PermissionEntity.class)
                .list();
    }
}
