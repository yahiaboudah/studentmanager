
package com.example.bouda.studentmanager.controller;

import com.example.bouda.studentmanager.model.Student;
import com.example.bouda.studentmanager.service.StudentService;
import com.example.bouda.studentmanager.service.SpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
@CrossOrigin(origins = "http://localhost:3000")
public class StudentController {
    
    @Autowired
    private StudentService studentService;
    @Autowired
    private SpecService specService;

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

                    student.setChoices(new ArrayList<>());

                    List<Choice> newChoices = new ArrayList<>();
                    for (int i = 0; i < studentDetails.getChoices().size(); i++) {
                        Choice choice = new Choice();
                        // choice.setTitle(studentDetails.getChoices().get(i).getSpec().getName());
                        choice.setChoiceOrder(i + 1);
                        Specialty specialty = specService.getSpecByName(studentDetails.getChoices());

                        choice.setSpecialty(specialty);
                        newChoices.add(choice);
                    }
                    student.setChoices(newChoices);

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
        return studentService.searchStudentsByFirstAndLastName(firstName, lastName);
        // return studentService.searchByFullName(firstName, lastName);
    }

    @GetMapping("/number")
    public List<Student> searchByStudentNumber(@RequestParam String num) {
        return studentService.searchStudentsByStudentNumber(num);
    }
}