package com.spareparts.store.service;

import com.spareparts.store.mapper.RoleMapper;
import com.spareparts.store.repository.PermissionRepository;
import com.spareparts.store.repository.RoleRepository;
import com.spareparts.store.repository.entity.RoleEntity;
import com.spareparts.store.service.model.ClientRole;

import java.util.Set;
import java.util.stream.Collectors;

public class RoleService {

    private RoleRepository roleRepository;
    private PermissionRepository permissionRepository;

    public void addRole(ClientRole role) {
        roleRepository.save(new RoleEntity(null, role.name()));
    }

    public void addPermissionToRole(long permissionId, long roleId) {

    }

    public ClientRole getDefaultRole() {

        return ClientRole.CLIENT;
    }

    Set<ClientRole> getClientRoles(long clientId) {

        return roleRepository.getClientRoles(clientId).stream().map(RoleMapper::toRole).collect(Collectors.toSet());

    }

}
