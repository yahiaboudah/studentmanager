package com.example.bouda.studentmanager.service;

import com.example.bouda.studentmanager.model.Spec;
import com.example.bouda.studentmanager.repository.SpecRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpecService {

    @Autowired
    private SpecRepository specRepository;

    public List<Spec> getAllSpecs() {
        return specRepository.findAll();
    }

    public Optional<Spec> getSpecById(Long id) {
        return specRepository.findById(id);
    }

    public Optional<Spec> getSpecByName(String title) {
        return specRepository.findSpecByName(title);
    }

    public Spec createOrUpdateSpec(Spec spec) {
        return specRepository.save(spec);
    }

    public void deleteSpec(Long id) {
        specRepository.deleteById(id);
    }
}
