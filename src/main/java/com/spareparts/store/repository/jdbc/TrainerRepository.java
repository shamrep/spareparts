package com.spareparts.store.repository.jdbc;

import com.spareparts.store.model.Trainer;

import java.util.List;
import java.util.Optional;

public interface TrainerRepository {
    Optional<Trainer> findById(Long id);
    List<Trainer> findAll();
    Optional<Trainer> save(Trainer trainer);
    void update(Trainer trainer);
    void delete(Long id);
    void deleteAll();
}
