package com.gymapp.repository.spring_jdbc;

import com.gymapp.repository.entity.NotificationEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public class NotificationEntityRepository {

    private final RowMapper<NotificationEntity> notificationRowMapper = (rs, rowNum) -> {

//        OffsetDateTime sendDateWithoutOffset = rs.getObject("send_date", OffsetDateTime.class);
//        OffsetDateTime sendDateWithOffset = sendDateWithoutOffset.toInstant().atOffset(ZoneOffset.ofTotalSeconds(rs.getInt("send_date_offset")));

        return new NotificationEntity(
                rs.getLong("id"),
                rs.getLong("client_id"),
                rs.getString("message"),
                rs.getObject("send_date", OffsetDateTime.class),
                rs.getBoolean("is_read"));
    };

    private final JdbcTemplate jdbcTemplate;

    public NotificationEntityRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public NotificationEntityRepository(JdbcTemplate template) {
        this.jdbcTemplate = template;
    }


    public Optional<NotificationEntity> findById(long id) {
        String sql = "select * from notifications where id = ?";

        List<NotificationEntity> notificationEntities = jdbcTemplate.query(sql, new Object[]{id}, notificationRowMapper);

        return notificationEntities.isEmpty() ? Optional.empty() : Optional.of(notificationEntities.get(0));
    }


    public List<NotificationEntity> findAll() {
        return List.of();
    }


    public long save(NotificationEntity notificationEntity) {
        String sql = "insert into notifications (client_id, message, send_date, is_read) values (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, notificationEntity.getClientId());
            ps.setString(2, notificationEntity.getMessage());
            ps.setObject(3, notificationEntity.getSendDate());
            ps.setBoolean(4, notificationEntity.isRead());

            return ps;
        }, keyHolder);

        if (keyHolder.getKey() != null) {
            return keyHolder.getKey().longValue();
        } else {
            throw new RuntimeException("Failed to retrieve generated key for Notification");
        }
    }


    public void update(NotificationEntity notificationEntity) {

    }


    public void deleteById(long id) {

    }


    public List<NotificationEntity> findUnreadNotifications() {
        return List.of();
    }
}
