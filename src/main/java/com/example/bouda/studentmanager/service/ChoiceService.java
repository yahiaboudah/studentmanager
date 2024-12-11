package com.example.bouda.studentmanager.service;

import com.example.bouda.studentmanager.model.Choice;
import com.example.bouda.studentmanager.repository.ChoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChoiceService {

    @Autowired
    private ChoiceRepository choiceRepository;

    public List<Choice> getAllChoices() {
        return choiceRepository.findAll();
    }

    public Optional<Choice> getChoiceById(Long id) {
        return choiceRepository.findById(id);
    }

    public Choice createOrUpdateChoice(Choice choice) {
        return choiceRepository.save(choice);
    }

    public void deleteChoice(Long id) {
        choiceRepository.deleteById(id);
    }

    public void deleteAllChoicesByStudentId(Long studentId) {
        List<Choice> choices = choiceRepository.findByStudentId(studentId);
        choiceRepository.deleteAll(choices);
    }

    public List<Choice> getAllChoicesByStudentId(Long studentId) {
        return choiceRepository.findByStudentId(studentId);
    }
} 