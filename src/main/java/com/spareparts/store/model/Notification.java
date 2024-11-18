package com.spareparts.store.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

@AllArgsConstructor
@Getter
public class Notification {
    private Long id;
    private long clientId;
    private String message;
    private OffsetDateTime sendDate;
    private boolean isRead;

    public OffsetDateTime getSendDate() {
        return sendDate.truncatedTo(ChronoUnit.MICROS);
    }
}
