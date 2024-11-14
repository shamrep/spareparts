package com.spareparts.store.model;

import java.time.LocalDateTime;

public record Membership (long id, Client client, LocalDateTime startDate, LocalDateTime endDate, double cost){
}
