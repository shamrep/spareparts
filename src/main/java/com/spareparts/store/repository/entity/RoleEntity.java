package com.spareparts.store.repository.entity;

import com.spareparts.store.service.model.ClientRole;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;


@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class RoleEntity {

    private Long id;
    private String name;

}
