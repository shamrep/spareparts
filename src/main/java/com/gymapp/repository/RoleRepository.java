package com.gymapp.repository;

import com.gymapp.repository.entity.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
public class RoleRepository {

    private final JdbcClient jdbcClient;

    public Optional<RoleEntity> findById(long id) {

        String selectQuery = "select * from roles where id = :id";

        return jdbcClient.sql(selectQuery)
                .param("id", id)
                .query(RoleEntity.class)
                .optional();
    }

    public long save(RoleEntity roleEntity) {

        String insertQuery = """
                    insert into roles (name)
                    values (:roleName)
                    returning id
                """;


        return jdbcClient.sql(insertQuery)
                .param("roleName", roleEntity.getName())
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

    public Set<RoleEntity> getClientRoles(long clientId) {

        return jdbcClient.sql("""
                        select 
                            roles.id, 
                            roles.name
                        from roles join clients on roles.client_id = clients.id
                        where client.id = :clientId
                        """)
                .param("clientId", clientId)
                .query(RoleEntity.class)
                .set();
        //how do they do it?
    }

}
