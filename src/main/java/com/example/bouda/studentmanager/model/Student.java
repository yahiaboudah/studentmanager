package com.example.bouda.studentmanager.model;

import jakarta.persistence.*;
import java.util.List;
import com.example.bouda.studentmanager.utils.MathUtils;

@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_number", unique = true, nullable = false)
    private String studentNumber;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "semester1_avg")
    private Double semester1Avg;

    @Column(name = "semester2_avg")
    private Double semester2Avg;

    @Column(name = "semester3_avg")
    private Double semester3Avg;
    
    @Column(name = "semester4_avg")
    private Double semester4Avg;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Choice> choices;

    private List<String> choicesList;

    @ManyToOne
    @JoinColumn(name = "spec_id")
    private Spec assignedSpec;

    @Transient
    public Double getOverallAverage() {
        return MathUtils.round((semester1Avg + semester2Avg + semester3Avg + semester4Avg) / 4.0);
    }

    public Student() {}

    // Getters

    public String getStudentNumber() {
        return studentNumber;
    }

    public Spec getAssignedSpec() {
        return this.assignedSpec;
    }

    public void setAssignedSpec(Spec assignedSpec) {
        this.assignedSpec = assignedSpec;
    }

    public List<String> getChoicesList(){
        return this.choicesList;
    }

    public void setChoiceList(List<String> choiceList) {
        this.choicesList = choicesList;
    }

    public Long getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public Double getSemester1Avg() {
        return semester1Avg;
    }

    public Double getSemester2Avg() {
        return semester2Avg;
    }

    public Double getSemester3Avg() {
        return semester3Avg;
    }

    public Double getSemester4Avg() {
        return semester4Avg;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSemester1Avg(Double semester1Avg) {
        this.semester1Avg = semester1Avg;
    }

    public void setSemester2Avg(Double semester2Avg) {
        this.semester2Avg = semester2Avg;
    }

    public void setSemester3Avg(Double semester3Avg) {
        this.semester3Avg = semester3Avg;
    }

    public void setSemester4Avg(Double semester4Avg) {
        this.semester4Avg = semester4Avg;
    }

    public void setChoices(List<Choice> choices) {
        if(this.choices != null) {
            this.choices.retainAll(choices);
        }
        this.choices = choices;
    }
}