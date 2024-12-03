package com.spareparts.store.repository.jdbc;

import java.util.List;
import java.util.Optional;

public interface TrainerRepository {
    Optional<TrainerEntity> findById(Long id);
    List<TrainerEntity> findAll();
    Optional<TrainerEntity> save(TrainerEntity trainerEntity);
    void update(TrainerEntity trainerEntity);
    void delete(Long id);
    void deleteAll();
}
