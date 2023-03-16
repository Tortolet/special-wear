package com.example.specialWear.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class SpecialWears {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @NotNull
    private String wearName;

    @NotNull
    @ElementCollection(targetClass = TypeOfWear.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "specialWear_typeOfWear", joinColumns = @JoinColumn(name = "specialWear_id"))
    @Enumerated(EnumType.STRING)
    private Set<TypeOfWear> typeSpecialWear;

    private LocalDateTime dateStart;

    private LocalDateTime dateEnd;

    @Max(12)
    @Min(message = "Необходимо начинать с 1", value = 1)
    @Column(nullable = false)
    private int wear_period_month;

    @Min(message = "Необходимо начинать с 1", value = 1)
    @Column(nullable = false)
    private float cost;

    @NotEmpty
    @NotNull
    private String desc;

    @NotEmpty
    @NotNull
    private String filename;

    public SpecialWears() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDateTime dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDateTime getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDateTime dateEnd) {
        this.dateEnd = dateEnd;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public Set<TypeOfWear> getTypeSpecialWear() {
        return typeSpecialWear;
    }

    public void setTypeSpecialWear(Set<TypeOfWear> typeSpecialWear) {
        this.typeSpecialWear = typeSpecialWear;
    }

    public int getWear_period_month() {
        return wear_period_month;
    }

    public void setWear_period_month(int wear_period_month) {
        this.wear_period_month = wear_period_month;
    }

    public void setWearName(String wearName) {
        this.wearName = wearName;
    }

    public String getWearName() {
        return wearName;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
