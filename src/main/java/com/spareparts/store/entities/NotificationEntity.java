package com.spareparts.store.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

@AllArgsConstructor
@Getter
public class NotificationEntity {
    private Long id;
    private long clientId;
    private String message;
    private OffsetDateTime sendDate;
    private boolean isRead;

    public OffsetDateTime getSendDate() {
        return sendDate.truncatedTo(ChronoUnit.MICROS);
    }
}
