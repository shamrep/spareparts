package com.spareparts.store.controller;


import com.spareparts.store.model.Part;
import com.spareparts.store.repository.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parts")
public class PartController {

    @Autowired
    private PartRepository partRepository;

    @GetMapping
    public List<Part> getAllParts() {
        return partRepository.findAll();
    }

    @PostMapping
    public void addPart(@RequestBody Part part) {
        partRepository.save(part);
    }
}
