package com.spareparts.store.repository;

import com.spareparts.store.repository.entity.RoleEntity;

import java.util.Optional;

public interface RoleRepository {
    Optional<RoleEntity> findById(long id);

    long save(RoleEntity roleEntity);
}
