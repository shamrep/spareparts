package com.spareparts.store.service;

import com.spareparts.store.model.Part;

import java.util.List;

public interface PartService {
    List<Part> getAllParts();

    Part getPartById(Long id);

    Part addPart(Part part);

    Part updatePart(Long id, Part part);

    void deletePart(Long id);
}
