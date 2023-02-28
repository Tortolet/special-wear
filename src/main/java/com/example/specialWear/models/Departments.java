package com.example.practica.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class Departments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String firstNameDirector;

    @NotNull
    private String secondNameDirector;

    @NotNull
    private String lastNameDirector;

    public Departments() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstNameDirector() {
        return firstNameDirector;
    }

    public void setFirstNameDirector(String firstNameDirector) {
        this.firstNameDirector = firstNameDirector;
    }

    public String getSecondNameDirector() {
        return secondNameDirector;
    }

    public void setSecondNameDirector(String secondNameDirector) {
        this.secondNameDirector = secondNameDirector;
    }

    public String getLastNameDirector() {
        return lastNameDirector;
    }

    public void setLastNameDirector(String lastNameDirector) {
        this.lastNameDirector = lastNameDirector;
    }
}
