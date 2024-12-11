package com.example.bouda.studentmanager.repository;

import com.example.bouda.studentmanager.model.Spec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecRepository extends JpaRepository<Spec, Long> {
    Optional<Spec> findSpecByName(String name);
}
