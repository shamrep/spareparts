package com.spareparts.store.controller;


import com.spareparts.store.model.Part;
import com.spareparts.store.service.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/parts")
public class PartController {

    @Autowired
    private PartService partService;

    // Get all parts
    @GetMapping
    public List<Part> getAllParts() {
        return partService.getAllParts();
    }

    // Get a part by ID
    @GetMapping("/{id}")
    public ResponseEntity<Part> getPartById(@PathVariable Long id) {
        Part part = partService.getPartById(id);
        return part != null ? ResponseEntity.ok(part) : ResponseEntity.notFound().build();
    }

    // Add a new part
    @PostMapping
    public Part addPart(@RequestBody Part part) {
        return partService.addPart(part);
    }

    // Update a part
    @PutMapping("/{id}")
    public ResponseEntity<Part> updatePart(@PathVariable Long id, @RequestBody Part part) {
        Part updatedPart = partService.updatePart(id, part);
        return updatedPart != null ? ResponseEntity.ok(updatedPart) : ResponseEntity.notFound().build();
    }

    // Delete a part
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePart(@PathVariable Long id) {
        partService.deletePart(id);
        return ResponseEntity.noContent().build();
    }

}
