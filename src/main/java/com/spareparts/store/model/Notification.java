package com.spareparts.store.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class Notification {
    private long id;
    private long clientId;
    private String message;
    private LocalDateTime sendDate;
    private boolean isRead;
}
