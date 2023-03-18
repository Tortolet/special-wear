package com.example.specialWear.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
public class CartHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Users user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private SizeCount sizeCount;

    @NotNull
    @NotEmpty
    private String orderNumber;

    public CartHistory() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public SizeCount getSizeCount() {
        return sizeCount;
    }

    public void setSizeCount(SizeCount sizeCount) {
        this.sizeCount = sizeCount;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
}
