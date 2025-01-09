package com.spareparts.store.repository;

import com.spareparts.store.repository.entity.PermissionEntity;
import com.spareparts.store.repository.entity.RoleEntity;
import com.spareparts.store.service.model.Permission;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RoleRepository {
    Optional<RoleEntity> findById(long id);

    long save(RoleEntity roleEntity);

    Optional<RoleEntity> findByName(String name);

    List<PermissionEntity> findPermissionsByRoleId(Long roleId);

    public Set<Permission> getRolePermissions(String roleName);

    void getClientRoles(long clientId);
}
