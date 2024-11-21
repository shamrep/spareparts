package com.spareparts.store.repository.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@AllArgsConstructor
@Getter
//@EqualsAndHashCode
public class MembershipEntity {
    private Long id;
    private long clientId;
    //todo: maybe enum?
    private MembershipType type; // e.g., Monthly, Annual

    public OffsetDateTime getStartDate() {
        return startDate.truncatedTo(ChronoUnit.MICROS);
    }

    public OffsetDateTime getEndDate() {
        return endDate.truncatedTo(ChronoUnit.MICROS);
    }

    private OffsetDateTime startDate;
    private OffsetDateTime endDate;
    private BigDecimal price;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MembershipEntity that = (MembershipEntity) o;
        return getClientId() == that.getClientId() && Objects.equals(getId(), that.getId()) && getType() == that.getType() && Objects.equals(getStartDate(), that.getStartDate()) && Objects.equals(getEndDate(), that.getEndDate()) && Objects.equals(getPrice(), that.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getClientId(), getType(), getStartDate(), getEndDate(), getPrice());
    }
}
