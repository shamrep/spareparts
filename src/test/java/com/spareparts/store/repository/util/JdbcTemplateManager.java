package com.spareparts.store.repository.util;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class JdbcTemplateManager {
    private static JdbcTemplate jdbcTemplate;

    public static JdbcTemplate getJdbcTemplate(DataSource dataSource) {
        if (jdbcTemplate == null) {
            jdbcTemplate = new JdbcTemplate(dataSource);
        }

        return jdbcTemplate;
    }
}
