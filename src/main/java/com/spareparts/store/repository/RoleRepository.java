package com.spareparts.store.repository;

import com.spareparts.store.repository.entity.PermissionEntity;
import com.spareparts.store.repository.entity.RoleEntity;
//import com.spareparts.store.service.model.Permission;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
public class RoleRepository {

    private JdbcClient jdbcClient;

    public RoleRepository() {
        this.jdbcClient = JdbcClient.create(DataSourceManager.getDataSource());
    }

    public Optional<RoleEntity> findById(long id) {

        String selectQuery = "select * from roles where id = :id";

        return jdbcClient.sql(selectQuery)
                .param("id", id)
                .query(RoleEntity.class)
                .optional();
    }

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

    public Optional<RoleEntity> findByName(String roleName) {

        return jdbcClient
                .sql("""
                select * from roles where name = :roleName
                """)
                .param("roleName", roleName)
                .query(RoleEntity.class)
                .optional();
    }

    public List<PermissionEntity> findPermissionsByRoleId(Long roleId) {

        return jdbcClient.sql("""
                select 
                    permissions.id, 
                    name, 
                    permissions.created_at 
                from 
                    permissions join role_permissions on permission.id = role_permissions.permission_id 
                where role_id = :roleId
                """)
                .param("roleId", roleId)
                .query(PermissionEntity.class)
                .list();
    }

//    public Set<Permission> getRolePermissions(String roleName) {
//        jdbcClient.sql("""
//                select * from permissions join roles on permissions.role_id = role.id
//                from
//                    permissions join role_permissions on permission.id = role_permissions.permission_id
//                where role_id = :roleId
//                """).param("roleId")
//        return null;
//    }

    public Set<RoleEntity> getClientRoles(long clientId) {

       return jdbcClient.sql("""
                select 
                    roles.id, 
                    roles.name, 
                    created_at 
                from roles join clients on roles.client_id = clients.id
                where client.id = :clientId
                """)
                .param("clientId", clientId)
                .query(RoleEntity.class)
               .set();
        //how do they do it?
    }
    
}
