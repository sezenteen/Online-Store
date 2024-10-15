package com.example.onlineshop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table( name ="category")

public class Category extends BaseEntity{

    private String name;
    @Column(name="name", length = 45)

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
