package com.example.practica.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Usages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Employees employee;

    @ManyToOne(fetch = FetchType.LAZY)
    private SpecialWears specialWear;

    // заполняется автоматически
    private Date dateStartUsed = new Date(System.currentTimeMillis());

    private Date dateStopUse; // в сервисе добавить сохранение времени через specialWear

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

    public Date getDateStartUsed() {
        return dateStartUsed;
    }

    public void setDateStartUsed(Date dateStartUsed) {
        this.dateStartUsed = dateStartUsed;
    }

    public boolean isEmployeeAgree() {
        return employeeAgree;
    }

    public void setEmployeeAgree(boolean employeeAgree) {
        this.employeeAgree = employeeAgree;
    }

    public Date getDateStopUse() {
        return dateStopUse;
    }

    public void setDateStopUse(Date dateStopUse) {
        this.dateStopUse = dateStopUse;
    }
}
