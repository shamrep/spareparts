package com.spareparts.store.service.model;

import java.time.OffsetDateTime;
import java.util.Set;

public class ClientTokenData {
    private long id;
    private String email;
    private Set<Role> roles;
    private OffsetDateTime exp;
}
