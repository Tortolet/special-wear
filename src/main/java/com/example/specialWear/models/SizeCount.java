package com.example.specialWear.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
public class SizeCount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @NotNull
    private String size;

    @Column(nullable = false)
    private int count;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private SpecialWears specialWears;

    public SizeCount() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public SpecialWears getSpecialWears() {
        return specialWears;
    }

    public void setSpecialWears(SpecialWears specialWears) {
        this.specialWears = specialWears;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
