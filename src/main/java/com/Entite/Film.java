package com.Entite;

import java.sql.Date;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import com.Utility.MyDate;
import jakarta.persistence.*;

@Entity
@Table(name = "film")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "duree", nullable = false)
    private float duree;

    @OneToMany(mappedBy = "film", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FilmCategorie> filmCategories = new HashSet<>();

    public Film() {
    }

    public Film(String nom, float duree) {
        this.nom = nom;
        this.duree = duree;
    }

    public Film(Long id, String nom, float duree) {
        this.id = id;
        this.nom = nom;
        this.duree = duree;
    }

    // Checks is an idCategorie is in this class's filmCategories
    public boolean categorieInFilm(Categorie categorie) {
        for (FilmCategorie fc : this.filmCategories) {
            if (fc.getCategorie().customEquals(categorie))
                return true;
        }
        return false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public float getDuree() {
        return duree;
    }

    public void setDuree(float duree) {
        this.duree = duree;
    }

    public Set<FilmCategorie> getFilmCategories() {
        return filmCategories;
    }

    public void setFilmCategories(Set<FilmCategorie> filmCategories) {
        this.filmCategories = filmCategories;
    }

    // Dans Film.java
    public void addFilmCategorie(FilmCategorie filmCategorie) {
        filmCategories.add(filmCategorie);
        System.out.println("added 1");
        filmCategorie.setFilm(this);
        System.out.println("added 2");
    }

    public void removeFilmCategorie(FilmCategorie filmCategorie) {
        filmCategories.remove(filmCategorie);
        filmCategorie.setFilm(null);
        filmCategorie.setCategorie(null);
    }

    public void removeAllFilmCategories() {

        filmCategories.clear();
    }
}
