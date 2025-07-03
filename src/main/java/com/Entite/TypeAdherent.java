package com.Entite;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "type_adherent")
public class TypeAdherent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String libelle;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // @OneToMany(mappedBy = "typeAdherent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // private List<Inscription> inscriptions = new ArrayList<>();

    // @OneToMany(mappedBy = "typeAdherent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // private List<ParametrePret> parametresPret = new ArrayList<>();

    // Constructeurs
    public TypeAdherent() {
    }

    public TypeAdherent(String libelle) {
        this.libelle = libelle;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // public List<Inscription> getInscriptions() {
    //     return inscriptions;
    // }

    // public void setInscriptions(List<Inscription> inscriptions) {
    //     this.inscriptions = inscriptions;
    // }

    // public List<ParametrePret> getParametresPret() {
    //     return parametresPret;
    // }

    // public void setParametresPret(List<ParametrePret> parametresPret) {
    //     this.parametresPret = parametresPret;
    // }
}