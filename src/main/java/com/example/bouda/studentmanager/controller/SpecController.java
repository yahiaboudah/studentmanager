package com.example.bouda.studentmanager.controller;

import com.example.bouda.studentmanager.model.Spec;
import com.example.bouda.studentmanager.service.SpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/spec")
@CrossOrigin(origins = "http://localhost:3000")
public class SpecController {

    @Autowired
    private SpecService specService;

    @GetMapping
    public List<Spec> getAllSpecs() {
        return specService.getAllSpecs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Spec> getSpecById(@PathVariable Long id) {
        return specService.getSpecById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Spec createSpec(@RequestBody Spec spec) {
        return specService.createOrUpdateSpec(spec);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Spec> updateSpec(@PathVariable Long id, @RequestBody Spec specDetails) {
        return specService.getSpecById(id)
                .map(spec -> {
                    spec.setName(specDetails.getName());
                    spec.setAvailablePlaces(specDetails.getAvailablePlaces());
                    return ResponseEntity.ok(specService.createOrUpdateSpec(spec));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSpec(@PathVariable Long id) {
        return specService.getSpecById(id)
                .map(spec -> {
                    specService.deleteSpec(id);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
} 