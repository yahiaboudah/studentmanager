package com.example.bouda.studentmanager.repository;

import com.example.bouda.studentmanager.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    
    List<Student> findByLastNameIgnoreCaseContaining(String lastName);
    List<Student> findByFirstNameIgnoreCaseContaining(String firstName);

    @Query("SELECT s FROM Student s ORDER BY (s.semester1Avg + s.semester2Avg + s.semester3Avg + s.semester4Avg) / 4.0 DESC")
    List<Student> findAllOrderedByOverallAverage();

    List<Student> findByFirstNameIgnoreCaseContainingAndLastNameIgnoreCaseContaining(String firstName, String lastName);

    List<Student> findByFirstNameIgnoreCaseContainingAndLastNameIgnoreCaseContainingAndStudentNumberContaining(String firstName, String lastName, String studentNumber);

    List<Student> findByFirstNameIgnoreCaseContainingAndStudentNumberContaining(String firstName, String studentNumber);
    List<Student> findByLastNameIgnoreCaseContainingAndStudentNumberContaining(String firstName, String studentNumber);
    


    List<Student> findByFirstNameIgnoreCaseContainingOrLastNameIgnoreCaseContaining(String firstName, String lastName);

    @Query("SELECT s FROM Student s WHERE s.studentNumber LIKE %:studentNumber%")
    List<Student> findByStudentNumberContaining(String studentNumber);
}