package com.spareparts.store.repository;

import com.spareparts.store.repository.entity.PermissionEntity;
import com.spareparts.store.repository.entity.RoleEntity;

import java.util.List;
import java.util.Optional;

public interface RoleRepository {
    Optional<RoleEntity> findById(long id);

    long save(RoleEntity roleEntity);

    Optional<RoleEntity> findByName(String name);

    List<PermissionEntity> findPermissionsByRoleId(Long roleId);
}
