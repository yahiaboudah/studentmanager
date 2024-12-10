package com.example.bouda.studentmanager.repository;

import com.example.bouda.studentmanager.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByLastNameIgnoreCase(String lastName);
    List<Student> findByFirstNameIgnoreCase(String firstName);

    @Query("SELECT s FROM Student s ORDER BY (s.semester1Avg + s.semester2Avg + s.semester3Avg + s.semester4Avg) / 4.0 DESC")
    List<Student> findAllOrderedByOverallAverage();
}