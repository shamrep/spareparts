package com.spareparts.store.mapper;

import com.spareparts.store.repository.entity.RoleEntity;
import com.spareparts.store.service.model.ClientRole;

public class RoleMapper {

    public static RoleEntity toRoleEntity(ClientRole role) {
        return new RoleEntity(null, role.name());
    }

    public static ClientRole toRole(RoleEntity entity) {
        return ClientRole.valueOf(entity.getName());
    }
}
