package com.spareparts.store.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;


@AllArgsConstructor
@Getter
//@EqualsAndHashCode
public class RoleEntity {
    private Long id;
    private String name;
    private OffsetDateTime createdAt;
//    private Long creatorId;

    public OffsetDateTime getCreatedAt() {
        return createdAt.truncatedTo(ChronoUnit.MICROS);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        RoleEntity that = (RoleEntity) object;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName()) && Objects.equals(getCreatedAt(), that.getCreatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getCreatedAt());
    }
}
