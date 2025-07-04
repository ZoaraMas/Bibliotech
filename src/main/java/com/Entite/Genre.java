package com.Entite;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "genre")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 100)
    private String nom;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // @OneToMany(mappedBy = "genre", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // private List<Livre> livres = new ArrayList<>();

    // @OneToMany(mappedBy = "genre", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // private List<ParametrePret> parametresPret = new ArrayList<>();

    // Constructeurs
    public Genre() {
    }

    public Genre(String nom) {
        this.nom = nom;
    }

    
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    // public List<Livre> getLivres() {
    //     return livres;
    // }

    // public void setLivres(List<Livre> livres) {
    //     this.livres = livres;
    // }

    // public List<ParametrePret> getParametresPret() {
    //     return parametresPret;
    // }

    // public void setParametresPret(List<ParametrePret> parametresPret) {
    //     this.parametresPret = parametresPret;
    // }
}