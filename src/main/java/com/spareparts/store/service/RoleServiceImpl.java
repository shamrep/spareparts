package com.spareparts.store.service;

import com.spareparts.store.repository.PermissionRepository;
import com.spareparts.store.repository.RoleRepository;
import com.spareparts.store.repository.entity.RoleEntity;
import com.spareparts.store.service.model.Permission;

import java.time.OffsetDateTime;
import java.util.Set;

public class RoleServiceImpl {

    private RoleRepository roleRepository;
    private PermissionRepository permissionRepository;

    public void addRole(String name) {
        roleRepository.save(new RoleEntity(null, name, OffsetDateTime.now()));
    }

    public void addPermissionToRole(long permissionId, long roleId) {

    }

    public Set<Permission> getRolePermissions(String roleName) {
        return roleRepository.getRolePermissions(roleName);
    }
}
