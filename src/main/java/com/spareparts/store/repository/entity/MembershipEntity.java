package com.spareparts.store.repository.entity;

import java.time.LocalDateTime;

public record MembershipEntity(long id, ClientEntity clientEntity, LocalDateTime startDate, LocalDateTime endDate, double cost){
}
