package com.gymapp.repository;

import com.gymapp.repository.entity.MembershipEntity;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.Optional;

@AllArgsConstructor
public class MembershipRepository {

    private JdbcClient jdbcClient;

    public Optional<MembershipEntity> findById(Long id) {

        return jdbcClient.sql("select * from memberships where id = ?;")
                .param(id)
                .query(MembershipEntity.class)
                .optional();
    }

    public long save(MembershipEntity membership) {

        return jdbcClient
                .sql("""
                        insert into memberships (client_id, type, start_date, end_date, price) 
                        values (?, CAST(? AS membership_type), ?, ?, ?) returning id;
                        """)
                .param(1, membership.getClientId())
                .param(2, membership.getType().name())
                .param(3, membership.getStartDate())
                .param(4, membership.getEndDate())
                .param(5, membership.getPrice())
                .query(Long.class)
                .single();
    }

    //todo: use new syntax
    public void update(MembershipEntity membership) {

       int rowsAffected = jdbcClient.sql("""
                update memberships set
                 client_id = ?,
                 type = CAST(? AS membership_type),
                 start_date = ?,
                 end_date = ?,
                 price = ? 
                 where id = ?;
                """)
               .param(membership.getClientId())
               .param(membership.getType().name())
               .param(membership.getStartDate())
               .param(membership.getEndDate())
               .param(membership.getPrice())
               .param(membership.getId())
               .update();
    }

    public void delete(Long id) {


        int rowsAffected = jdbcClient.sql("delete from memberships where id = :membershipId;")
                .param("membershipId", id)
                .update();
//todo: ok?
        if (rowsAffected == 0) {
            throw new IllegalArgumentException("No membership found with ID: " + id);
        }
    }
}
