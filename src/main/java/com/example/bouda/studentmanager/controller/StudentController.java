
package com.example.bouda.studentmanager.controller;

import com.example.bouda.studentmanager.model.Student;
import com.example.bouda.studentmanager.model.Choice;
import com.example.bouda.studentmanager.model.Spec;

import com.example.bouda.studentmanager.service.StudentService;
import com.example.bouda.studentmanager.service.ChoiceService;
import com.example.bouda.studentmanager.service.SpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/student")
@CrossOrigin(origins = "http://localhost:3000")
public class StudentController {
    
    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;
    @Autowired
    private SpecService specService;
    @Autowired
    private ChoiceService choiceService;

    @GetMapping
    public List<Student> getAllStudents() {
        logger.info("Getting all students");
        return studentService.getAllStudentsOrderedByAverage();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Map<String, Object> studentDetails) {
        
        Student student = new Student();

        student.setFirstName((String) studentDetails.get("firstName"));
        student.setLastName((String) studentDetails.get("lastName"));
        student.setSemester1Avg(Double.valueOf(studentDetails.get("semester1Avg").toString()));
        student.setSemester2Avg(Double.valueOf(studentDetails.get("semester2Avg").toString()));
        student.setSemester3Avg(Double.valueOf(studentDetails.get("semester3Avg").toString()));
        student.setSemester4Avg(Double.valueOf(studentDetails.get("semester4Avg").toString()));

        List<Choice> newChoices = new ArrayList<>();
        List<String> choicesList = (List<String>) studentDetails.get("choicesList");

        for (int i = 0; i < choicesList.size(); i++) {
            
            Choice choice = new Choice();
            Spec specialty = specService.getSpecByName((String) choicesList.get(i));

            choice.setStudent(student);
            choice.setSpec(specialty);
            choice.setChoiceOrder(i + 1);
            newChoices.add(choice);
        }
        student.setChoices(newChoices);

        return ResponseEntity.ok(studentService.createOrUpdateStudent(student));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Map<String, Object> studentDetails) {

        logger.info("Updating student with ID: {}. Request body: {}", id, studentDetails);
        // return;

        return studentService.getStudentById(id)
                .map(student -> {
                    
                    student.setFirstName((String) studentDetails.get("firstName"));
                    student.setLastName((String) studentDetails.get("lastName"));
                    student.setSemester1Avg(Double.valueOf(studentDetails.get("semester1Avg").toString()));
                    student.setSemester2Avg(Double.valueOf(studentDetails.get("semester2Avg").toString()));
                    student.setSemester3Avg(Double.valueOf(studentDetails.get("semester3Avg").toString()));
                    student.setSemester4Avg(Double.valueOf(studentDetails.get("semester4Avg").toString()));


                    choiceService.deleteAllChoicesByStudentId(student.getId());
                    student.getChoices().clear();

                    List<Choice> newChoices = new ArrayList<>();

                    // logger.info("My Size is {}", studentDetails.get("choicesList").size());

                    List<String> choicesList = (List<String>) studentDetails.get("choicesList");

                    for (int i = 0; i < choicesList.size(); i++) {
                        
                        Choice choice = new Choice();
                        Spec specialty = specService.getSpecByName((String) choicesList.get(i));

                        choice.setStudent(student);
                        choice.setSpec(specialty);
                        choice.setChoiceOrder(i + 1);
                        newChoices.add(choice);
                        logger.warn("Adding new choice for student!");
                    }
                    student.setChoices(newChoices);
                    logger.info("Added all choices to student!");

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

    @GetMapping("/fullSearch")
    public List<Student> searchStudents(@RequestParam(required= false) String firstName, @RequestParam(required= false) String lastName, @RequestParam(required= false) String number) {
        return studentService.searchStudentByNameAndNumber(firstName, lastName, number);
    }

    @GetMapping("/number")
    public List<Student> searchByStudentNumber(@RequestParam String num) {
        return studentService.searchStudentsByStudentNumber(num);
    }
}