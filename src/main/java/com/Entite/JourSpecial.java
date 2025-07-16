package com.Entite;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.enums.TypeSpecial;

@Entity
@Table(name = "jour_special")
public class JourSpecial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date")
    private java.time.LocalDate date;

    @Column(name = "type")
    private TypeSpecial type;

    public JourSpecial() {
    }

    public JourSpecial(Integer id, LocalDate date, TypeSpecial type) {
        this.id = id;
        this.date = date;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public java.time.LocalDate getDate() {
        return date;
    }

    public void setDate(java.time.LocalDate date) {
        this.date = date;
    }

    public TypeSpecial getType() {
        return type;
    }

    public void setType(TypeSpecial type) {
        this.type = type;
    }

}