package com.example.bouda.studentmanager.model;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "spec")
public class Spec {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "available_places")
    private Integer availablePlaces;

    @OneToMany(mappedBy = "spec", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Choice> choices;

    // Constructors
    public Spec() {
    }

    public Spec(String name, Integer availablePlaces) {
        this.name = name;
        this.availablePlaces = availablePlaces;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAvailablePlaces() {
        return availablePlaces;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAvailablePlaces(Integer availablePlaces) {
        this.availablePlaces = availablePlaces;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }
}