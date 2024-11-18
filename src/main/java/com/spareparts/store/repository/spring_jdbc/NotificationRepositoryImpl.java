package com.spareparts.store.repository.spring_jdbc;

import com.spareparts.store.model.Notification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

public class NotificationRepositoryImpl implements NotificationRepository {

    private final RowMapper<Notification> notificationRowMapper = (rs, rowNum) -> {

//        OffsetDateTime sendDateWithoutOffset = rs.getObject("send_date", OffsetDateTime.class);
//        OffsetDateTime sendDateWithOffset = sendDateWithoutOffset.toInstant().atOffset(ZoneOffset.ofTotalSeconds(rs.getInt("send_date_offset")));

        return new Notification(
                rs.getLong("id"),
                rs.getLong("client_id"),
                rs.getString("message"),
                rs.getObject("send_date", OffsetDateTime.class),
                rs.getBoolean("is_read"));
    };

    private final JdbcTemplate jdbcTemplate;

    public NotificationRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public NotificationRepositoryImpl(JdbcTemplate template) {
        this.jdbcTemplate = template;
    }

    @Override
    public Optional<Notification> findById(long id) {
        String sql = "select * from notifications where id = ?";

        List<Notification> notifications = jdbcTemplate.query(sql, new Object[]{id}, notificationRowMapper);

        return notifications.isEmpty() ? Optional.empty() : Optional.of(notifications.get(0));
    }

    @Override
    public List<Notification> findAll() {
        return List.of();
    }

    @Override
    public long save(Notification notification) {
        String sql = "insert into notifications (client_id, message, send_date, is_read) values (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, notification.getClientId());
            ps.setString(2, notification.getMessage());
            ps.setObject(3, notification.getSendDate());
            ps.setBoolean(4, notification.isRead());

            return ps;
        }, keyHolder);

        if (keyHolder.getKey() != null) {
            return keyHolder.getKey().longValue();
        } else {
            throw new RuntimeException("Failed to retrieve generated key for Notification");
        }
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
