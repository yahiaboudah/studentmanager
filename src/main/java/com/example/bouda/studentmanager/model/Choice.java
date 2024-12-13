package com.example.bouda.studentmanager.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "choice")
public class Choice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "spec_id", nullable = false)
    private Spec spec;

    @Column(name = "choice_order")
    private Integer choiceOrder;

    private String title;

    // Constructors
    public Choice() {
    }

    public Choice(Student student, Spec spec, Integer choiceOrder) {
        this.student = student;
        this.spec = spec;
        this.choiceOrder = choiceOrder;
    }
    // Getters
    public Long getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public Spec getSpec() {
        return spec;
    }

    public Integer getChoiceOrder() {
        return choiceOrder;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setSpec(Spec spec) {
        this.spec = spec;
    }

    public void setChoiceOrder(Integer choiceOrder) {
        this.choiceOrder = choiceOrder;
    }
}