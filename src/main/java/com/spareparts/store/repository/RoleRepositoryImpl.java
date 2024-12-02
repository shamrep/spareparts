package com.spareparts.store.repository;

import com.spareparts.store.repository.entity.RoleEntity;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.Optional;

public class RoleRepositoryImpl implements RoleRepository {

    private JdbcClient jdbcClient;

    @Override
    public Optional<RoleEntity> findById(long id) {


        return Optional.empty();
    }

    @Override
    public long save(RoleEntity roleEntity) {
        return 0;
    }
}
