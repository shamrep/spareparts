package com.spareparts.store.repository;

import com.spareparts.store.model.Part;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartRepository extends JpaRepository<Part, Long> {

}
