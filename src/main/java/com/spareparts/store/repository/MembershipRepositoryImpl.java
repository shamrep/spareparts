package com.spareparts.store.repository;

import com.spareparts.store.repository.entity.MembershipEntity;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.Optional;

@AllArgsConstructor
public class MembershipRepositoryImpl implements MembershipRepository {

    //    private JdbcTemplate jdbcTemplate;
    private JdbcClient jdbcClient;

    @Override
    public Optional<MembershipEntity> findById(Long id) {

        String sql = "select * from memberships where id = ?;";

        return jdbcClient.sql(sql)
                .param(id)
                .query(MembershipEntity.class)
                .optional();
    }

    @Override
    public void save(MembershipEntity membership) {

    }

    @Override
    public void update(MembershipEntity membership) {

    }

    @Override
    public void delete(Long id) {

    }
}
