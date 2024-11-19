package com.spareparts.store.repository;

import com.spareparts.store.entity.MembershipEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipEntityRepository extends JpaRepository<MembershipEntity, Long> {
}
