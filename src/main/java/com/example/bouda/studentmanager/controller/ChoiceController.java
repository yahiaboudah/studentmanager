package com.example.bouda.studentmanager.controller;

import com.example.bouda.studentmanager.model.Choice;
import com.example.bouda.studentmanager.model.Spec;
import com.example.bouda.studentmanager.model.Student;
import com.example.bouda.studentmanager.service.ChoiceService;
import com.example.bouda.studentmanager.service.SpecService;
import com.example.bouda.studentmanager.service.StudentService;
import com.example.bouda.studentmanager.repository.ChoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/choices")
@CrossOrigin(origins = "http://localhost:3000")
public class ChoiceController {


    private static final Logger logger = LoggerFactory.getLogger(ChoiceController.class);
    
    @Autowired
    private StudentService studentService;
    @Autowired
    private SpecService specService;
    @Autowired
    private ChoiceService choiceService;

    private final Random random = new Random();

    @GetMapping
    public List<Choice> getAllChoices() {
        return choiceService.getAllChoices();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Choice> getChoiceById(@PathVariable Long id) {
        return choiceService.getChoiceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Choice createChoice(@RequestBody Choice choice) {
        return choiceService.createOrUpdateChoice(choice);
    }

    @GetMapping("/assign")
    public ResponseEntity<Boolean> assignSpecs() {
        try {
            
            List<Student> students = studentService.getAllStudentsOrderedByAverage();
            List<Spec> specs = specService.getAllSpecs();

            Map<Long, Integer> originalAvailablePlaces = new HashMap<>();
            for (Spec spec : specs) {
                originalAvailablePlaces.put(spec.getId(), spec.getAvailablePlaces());
            }

            for(Student student: students) {

                student.setAssignedSpec(null);

                List<Choice> choices = student.getChoices();
                for(Choice choice: choices) {
                    
                    Spec matchingSpec = specs.stream()
                                 .filter(spec -> spec.getId().equals(choice.getSpec().getId()))
                                 .findFirst().get();
                    
                    if(matchingSpec.getAvailablePlaces() > 0) {
                        student.setAssignedSpec(matchingSpec);
                        matchingSpec.setAvailablePlaces(matchingSpec.getAvailablePlaces()-1);
                        break;
                    } else {
                        logger.info("Not enough places in {} for student {}", matchingSpec.getName(), student.getLastName());
                        continue;
                    }
                }
                studentService.createOrUpdateStudent(student);
            }

             for (Spec spec : specs) {
                spec.setAvailablePlaces(originalAvailablePlaces.get(spec.getId()));
                specService.createOrUpdateSpec(spec);
            }

            return ResponseEntity.ok(true);
        }

        catch(Exception e) {
            return ResponseEntity.ok(false);
        }
    }

    @GetMapping("/refresh")
    public ResponseEntity<Boolean> refreshChoices() {

        try {
            choiceService.deleteAllChoices();
            List<Spec> specsList = specService.getAllSpecs();
            List<Student> students = studentService.getAllStudents();

            for(Student student: students) {
                
                List<Choice> newChoices = new ArrayList<>();
                List<Integer> usedIndices = new ArrayList<>();
                
                for(int j=0; j<specsList.size(); j++) {
                    
                    Choice choice = new Choice();
                    
                    int randomIndex;
                    do {
                        randomIndex = random.nextInt(specsList.size());
                    } while (usedIndices.contains(randomIndex));

                    usedIndices.add(randomIndex);

                    Spec specialty = specsList.get(randomIndex);
                    choice.setStudent(student);
                    choice.setSpec(specialty);
                    choice.setChoiceOrder(j + 1);
                    newChoices.add(choice);
                }

                student.setChoices(newChoices);

                studentService.createOrUpdateStudent(student);
            }

            return ResponseEntity.ok(true);
        }
        catch(Exception e) {
            return ResponseEntity.ok(false);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Choice> updateChoice(@PathVariable Long id, @RequestBody Choice choiceDetails) {
        return choiceService.getChoiceById(id)
                .map(choice -> {
                    choice.setStudent(choiceDetails.getStudent());
                    choice.setSpec(choiceDetails.getSpec());
                    choice.setChoiceOrder(choiceDetails.getChoiceOrder());
                    return ResponseEntity.ok(choiceService.createOrUpdateChoice(choice));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteChoice(@PathVariable Long id) {
        return choiceService.getChoiceById(id)
                .map(choice -> {
                    choiceService.deleteChoice(id);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/student/{studentId}")
    public ResponseEntity<?> deleteAllChoicesByStudentId(@PathVariable Long studentId) {
        choiceService.deleteAllChoicesByStudentId(studentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/student/{studentId}")
    public List<Choice> getAllChoicesByStudentId(@PathVariable Long studentId) {
        return choiceService.getAllChoicesByStudentId(studentId);
    }
}
