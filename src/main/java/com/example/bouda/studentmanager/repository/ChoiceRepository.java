package com.example.bouda.studentmanager.repository;

import com.example.bouda.studentmanager.model.Choice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChoiceRepository extends JpaRepository<Choice, Long> {
    List<Choice> findByStudentId(Long studentId);
} 