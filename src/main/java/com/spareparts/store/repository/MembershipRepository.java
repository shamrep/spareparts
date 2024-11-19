package com.spareparts.store.repository;

import com.spareparts.store.repository.entity.MembershipEntity;

import java.util.Optional;

public interface MembershipRepository {
    Optional<MembershipEntity> findById(Long id);
    void save(MembershipEntity membership);
    void update(MembershipEntity membership);
    void delete(Long id);
}
