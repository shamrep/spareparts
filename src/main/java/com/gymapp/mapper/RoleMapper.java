package com.gymapp.mapper;

import com.gymapp.repository.entity.RoleEntity;
import com.gymapp.service.model.ClientRole;

public class RoleMapper {

    public static RoleEntity toRoleEntity(ClientRole role) {
        return new RoleEntity(null, role.name());
    }

    public static ClientRole toRole(RoleEntity entity) {
        return ClientRole.valueOf(entity.getName());
    }
}
