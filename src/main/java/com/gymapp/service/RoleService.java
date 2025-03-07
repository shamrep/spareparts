package com.gymapp.service;

import com.gymapp.mapper.RoleMapper;
import com.gymapp.repository.RoleRepository;
import com.gymapp.repository.entity.RoleEntity;
import com.gymapp.service.model.ClientRole;

import java.util.Set;
import java.util.stream.Collectors;

public class RoleService {

    private RoleRepository roleRepository;

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
