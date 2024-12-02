package com.spareparts.store.repository;

import com.spareparts.store.repository.entity.PermissionEntity;

import java.util.Optional;

public interface PermissionRepository {
    Optional<PermissionEntity> findById(long id);

    long save(PermissionEntity permissionEntity);
}
