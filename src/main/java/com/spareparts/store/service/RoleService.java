package com.spareparts.store.service;

import com.spareparts.store.mapper.RoleMapper;
import com.spareparts.store.repository.PermissionRepository;
import com.spareparts.store.repository.RoleRepository;
import com.spareparts.store.repository.entity.RoleEntity;

import com.spareparts.store.service.model.Role;
import com.spareparts.store.service.model.RoleEnum;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class RoleService {

    private RoleRepository roleRepository;
    private PermissionRepository permissionRepository;

    public void addRole(RoleEnum name) {
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

//    public Set<Permission> getRolePermissions(String roleName) {
//
//        return roleRepository.getRolePermissions(roleName);
//
//    }

    Set<Role> getClientRoles(long clientId) {

        return roleRepository.getClientRoles(clientId).stream().map(RoleMapper::toRole).collect(Collectors.toSet());

    }
}
