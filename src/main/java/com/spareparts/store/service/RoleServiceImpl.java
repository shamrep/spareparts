package com.spareparts.store.service;

import com.spareparts.store.mapper.RoleMapper;
import com.spareparts.store.repository.PermissionRepository;
import com.spareparts.store.repository.RoleRepository;
import com.spareparts.store.repository.entity.RoleEntity;
import com.spareparts.store.service.model.Permission;
import com.spareparts.store.service.model.Role;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.Set;

public class RoleServiceImpl {

    private RoleRepository roleRepository;
    private PermissionRepository permissionRepository;

    public void addRole(String name) {
        roleRepository.save(new RoleEntity(null, name, OffsetDateTime.now()));
    }

    public void addPermissionToRole(long permissionId, long roleId) {

    }

    public Role getDefaultRole() {

        Optional<RoleEntity> optionalRoleEntity = roleRepository.findByName("client");

        if (optionalRoleEntity.isEmpty()) {

            throw new RuntimeException("Not found default role.");

        }

        return RoleMapper.toRole(optionalRoleEntity.get());
    }

    public Set<Permission> getRolePermissions(String roleName) {

        return roleRepository.getRolePermissions(roleName);

    }

    Set<Role> getClientRoles(long clientId) {

        roleRepository.getClientRoles(clientId);

    }
}
