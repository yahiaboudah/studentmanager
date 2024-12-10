package com.example.bouda.studentmanager.service;

import com.example.bouda.studentmanager.utils.MathUtils;
import com.example.bouda.studentmanager.model.Student;
import com.example.bouda.studentmanager.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    
    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudentsOrderedByAverage() {
        List<Student> students = studentRepository.findAllOrderedByOverallAverage();

        students.forEach(student -> {
            student.setSemester1Avg(MathUtils.round(student.getSemester1Avg()));
            student.setSemester2Avg(MathUtils.round(student.getSemester2Avg()));
            student.setSemester3Avg(MathUtils.round(student.getSemester3Avg()));
            student.setSemester4Avg(MathUtils.round(student.getSemester4Avg()));
        });

        return students;
    }


    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Student createOrUpdateStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public List<Student> searchByFullName(String firstName, String lastName) {
        return studentRepository.findByFirstNameIgnoreCaseContainingOrLastNameIgnoreCaseContaining(firstName, lastName);
    }

    public List<Student> searchStudentsByFirstAndLastName(String firstName, String lastName) {
        if (firstName != null && lastName != null) {
            return studentRepository.findByFirstNameIgnoreCaseContainingAndLastNameIgnoreCaseContaining(firstName, lastName);
        } else if (firstName != null) {
            return studentRepository.findByFirstNameIgnoreCaseContaining(firstName);
        } else if (lastName != null) {
            return studentRepository.findByLastNameIgnoreCaseContaining(lastName);
        } else {
            return studentRepository.findAll();
        }
    }

    public List<Student> searchStudentsByStudentNumber(String studentNumber) {
        return studentRepository.findByStudentNumberContaining(studentNumber);
    }

    // public List<Student> searchByLastName(String lastName) {
    //     return studentRepository.findByLastNameIgnoreCase(lastName);
    // }
}