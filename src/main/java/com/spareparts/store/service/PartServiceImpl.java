package com.spareparts.store.service;

import com.spareparts.store.model.Part;
import com.spareparts.store.repository.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartServiceImpl implements PartService {

    @Autowired
    private PartRepository partRepository;

    @Override
    public List<Part> getAllParts() {
        return partRepository.findAll();
    }

    @Override
    public Part getPartById(Long id) {
        return partRepository.getReferenceById(id);
    }

    @Override
    public Part addPart(Part part) {
        return partRepository.save(part);
    }

    //find method for update
    @Override
    public Part updatePart(Long id, Part part) {
        return null;
    }

    @Override
    public void deletePart(Long id) {
        partRepository.deleteById(id);
    }
}
