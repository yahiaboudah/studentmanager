package com.example.bouda.studentmanager.repository;

import com.example.bouda.studentmanager.model.Spec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecRepository extends JpaRepository<Spec, Long> {
    
    Spec findSpecByName(String name);

    List<Spec> findByNameIgnoreCaseContaining(String name);
}
