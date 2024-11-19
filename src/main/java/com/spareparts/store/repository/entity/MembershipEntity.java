package com.spareparts.store.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@AllArgsConstructor
@Getter
public class MembershipEntity {
    private Long id;
    private ClientEntity client;
    //todo: maybe enum?
    private MembershipType type; // e.g., Monthly, Annual
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;
    private BigDecimal price;
}
