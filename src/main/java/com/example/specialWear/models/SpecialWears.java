package com.example.specialWear.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class SpecialWears {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ElementCollection(targetClass = TypeOfWear.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "specialWear_typeOfWear", joinColumns = @JoinColumn(name = "specialWear_id"))
    @Enumerated(EnumType.STRING)
    private Set<TypeOfWear> typeSpecialWear;

//    private Date dateStart = new Date(System.currentTimeMillis());
//
//
//    private Date dateEnd = new Date(dateStart.getYear(), dateStart.getMonth() + 6, dateStart.getDay(), dateStart.getHours(), dateStart.getMinutes());

    @Max(12)
    @NotNull
    private int wear_period_month;

    @NotNull
    private int cost;

    public SpecialWears() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public Date getDateStart() {
//        return dateStart;
//    }
//
//    public void setDateStart(Date dateStart) {
//        this.dateStart = dateStart;
//    }
//
//    public Date getDateEnd() {
//        return dateEnd;
//    }
//
//    public void setDateEnd(Date dateEnd) {
//        this.dateEnd = dateEnd;
//    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
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
}
