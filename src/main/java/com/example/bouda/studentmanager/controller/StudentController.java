
package com.example.bouda.studentmanager.controller;

import com.example.bouda.studentmanager.model.Student;
import com.example.bouda.studentmanager.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudentsOrderedByAverage();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createOrUpdateStudent(student);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
        return studentService.getStudentById(id)
                .map(student -> {
                    student.setFirstName(studentDetails.getFirstName());
                    student.setLastName(studentDetails.getLastName());
                    student.setSemester1Avg(studentDetails.getSemester1Avg());
                    student.setSemester2Avg(studentDetails.getSemester2Avg());
                    student.setSemester3Avg(studentDetails.getSemester3Avg());
                    student.setSemester4Avg(studentDetails.getSemester4Avg());
                    return ResponseEntity.ok(studentService.createOrUpdateStudent(student));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        return studentService.getStudentById(id)
                .map(student -> {
                    studentService.deleteStudent(id);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public List<Student> searchStudents(@RequestParam(required= false) String firstName, @RequestParam(required= false) String lastName) {
        
        if (firstName != null && lastName != null) {
            return studentRepository.findByFirstNameAndLastName(firstName, lastName);
        } else if (firstName != null) {
            return studentRepository.findByFirstName(firstName);
        } else if (lastName != null) {
            return studentRepository.findByLastName(lastName);
        } else {
            return studentRepository.findAll();
        }
        // return studentService.searchByFullName(firstName, lastName);
    }

    @GetMapping("/number")
    public List<Student> searchByStudentNumber(@RequestParam String num) {
        return studentService.searchStudentsByStudentNumber(num);
    }
}