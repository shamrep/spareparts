package com.spareparts.store.mapper;

import com.spareparts.store.repository.entity.RoleEntity;
import com.spareparts.store.service.model.Role;


public class RoleMapper {

    public static RoleEntity toRoleEntity(Role role) {

        return new RoleEntity(role.getId(), role.getName(), role.getCreatedAt());

    }

    public static Role toRole(RoleEntity entity) {

        return new Role(entity.getId(), entity.getName(), entity.getCreatedAt(), null);

    }
}
