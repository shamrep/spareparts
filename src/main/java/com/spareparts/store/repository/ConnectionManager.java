package com.spareparts.store.repository;

import java.sql.Connection;

public interface ConnectionManager {
    Connection getConnection();
}
