package com.spareparts.store.repository;

import com.spareparts.store.model.Trainer;

import java.util.List;
import java.util.Optional;

public interface TrainerRepository {
    Optional<Trainer> findById(Long id);
    List<Trainer> findAll();
    void save(Trainer customer);
    void update(Trainer customer);
    void delete(Long id);
}
