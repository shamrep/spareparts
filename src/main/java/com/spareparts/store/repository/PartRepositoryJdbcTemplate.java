package com.spareparts.store.repository;

import com.spareparts.store.model.Part;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PartRepositoryJdbcTemplate {

    private final JdbcTemplate jdbcTemplate;

    public PartRepositoryJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Part> findAll() {
        String sql = "SELECT * FROM parts";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Part(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getBigDecimal("price")
        ));
    }

    public void save(Part part) {
        String sql = "INSERT INTO parts (name, description, price) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, part.getName(), part.getDescription(), part.getPrice());
    }
}
