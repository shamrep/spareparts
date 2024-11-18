package com.spareparts.store.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;

@AllArgsConstructor
@Getter
public class Notification {
    private Long id;
    private long clientId;
    private String message;
    private OffsetDateTime sendDate;
    private boolean isRead;
}
