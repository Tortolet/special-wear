package com.example.specialWear.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Usages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Employees employee;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private SpecialWears specialWear;

    private LocalDateTime dateStartUsed;

    private LocalDateTime dateStopUse;

    private boolean employeeAgree;

    public Usages() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employees getEmployee() {
        return employee;
    }

    public void setEmployee(Employees employee) {
        this.employee = employee;
    }

    public SpecialWears getSpecialWear() {
        return specialWear;
    }

    public void setSpecialWear(SpecialWears specialWear) {
        this.specialWear = specialWear;
    }

    public LocalDateTime getDateStartUsed() {
        return dateStartUsed;
    }

    public void setDateStartUsed(LocalDateTime dateStartUsed) {
        this.dateStartUsed = dateStartUsed;
    }

    public boolean isEmployeeAgree() {
        return employeeAgree;
    }

    public void setEmployeeAgree(boolean employeeAgree) {
        this.employeeAgree = employeeAgree;
    }

    public LocalDateTime getDateStopUse() {
        return dateStopUse;
    }

    public void setDateStopUse(LocalDateTime dateStopUse) {
        this.dateStopUse = dateStopUse;
    }
}
