package com.example.specialWear.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Departments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String firstNameDirector;

    @NotNull
    @NotEmpty
    private String secondNameDirector;

    @NotNull
    @NotEmpty
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
