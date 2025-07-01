package com.Entite;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name = "categorie")
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "libelle", nullable = false)
    private String libelle;

    @OneToMany(mappedBy = "categorie", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FilmCategorie> filmCategories = new HashSet<>();

    public Categorie() {
    }

    public Categorie(String libelle) {
        this.libelle = libelle;
    }

    public Categorie(Long id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }

    public boolean customEquals(Categorie categorie) {
        System.out.println("here");
        if (categorie.getId().equals(this.getId()))
            return true;
        return false;
    }

    public String disp() {
        return getId() + " - " + getLibelle();
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<FilmCategorie> getFilmCategories() {
        return filmCategories;
    }

    public void setFilmCategories(Set<FilmCategorie> filmCategories) {
        this.filmCategories = filmCategories;
    }

}
