package com.spareparts.store.repository.spring_jdbc;

import com.spareparts.store.model.Notification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Optional;

public class NotificationRepositoryImpl implements NotificationRepository {

    RowMapper<Notification> notificationRawMapper = (rs, rowNum) -> new Notification(
            rs.getLong("id"),
            rs.getLong("client_id"),
            rs.getString("message"),
            rs.getTimestamp("sendDate").toLocalDateTime(),
            rs.getBoolean("isRead"));

    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Notification> findById(long id) {
        String sql = "select * from notifications where id = ?";

        return jdbcTemplate.query(sql, notificationRawMapper).stream().findFirst();
    }

    @Override
    public List<Notification> findAll() {
        return List.of();
    }

    @Override
    public long save(Notification notification) {
        return 0;
    }

    @Override
    public void update(Notification notification) {

    }

    @Override
    public void deleteById(long id) {

    }

    @Override
    public List<Notification> findUnreadNotifications() {
        return List.of();
    }
}
