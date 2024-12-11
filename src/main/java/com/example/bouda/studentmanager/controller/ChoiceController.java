package com.example.bouda.studentmanager.controller;

import com.example.bouda.studentmanager.model.Choice;
import com.example.bouda.studentmanager.service.ChoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/choices")
@CrossOrigin(origins = "http://localhost:3000")
public class ChoiceController {

    @Autowired
    private ChoiceService choiceService;

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
