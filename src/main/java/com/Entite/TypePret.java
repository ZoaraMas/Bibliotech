package com.Entite;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "type_pret")
public class TypePret {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String libelle;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // @OneToMany(mappedBy = "typePret", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // private List<Pret> prets = new ArrayList<>();

    // @OneToMany(mappedBy = "typePret", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // private List<Reservation> reservations = new ArrayList<>();

    // @OneToMany(mappedBy = "typePret", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // private List<ParametrePret> parametresPret = new ArrayList<>();

    // Constructeurs
    public TypePret() {
    }

    public TypePret(String libelle) {
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

    // public List<Pret> getPrets() {
    //     return prets;
    // }

    // public void setPrets(List<Pret> prets) {
    //     this.prets = prets;
    // }

    // public List<Reservation> getReservations() {
    //     return reservations;
    // }

    // public void setReservations(List<Reservation> reservations) {
    //     this.reservations = reservations;
    // }

    // public List<ParametrePret> getParametresPret() {
    //     return parametresPret;
    // }

    // public void setParametresPret(List<ParametrePret> parametresPret) {
    //     this.parametresPret = parametresPret;
    // }
}
