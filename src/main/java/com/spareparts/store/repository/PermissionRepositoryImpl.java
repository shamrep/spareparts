package com.spareparts.store.repository;

import com.spareparts.store.repository.entity.PermissionEntity;

import java.util.Optional;

public class PermissionRepositoryImpl implements PermissionRepository{
    @Override
    public Optional<PermissionEntity> findById(long id) {
        return Optional.empty();
    }

    @Override
    public long save(PermissionEntity permissionEntity) {
        return 0;
    }
}
