package com.Entite;

import java.util.Objects;

import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "film_categorie")
public class FilmCategorie {
    @EmbeddedId
    private FilmCategorieId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idFilm") // map to the FilmCategoriId
    @JoinColumn(name = "id_film", referencedColumnName = "id")
    private Film film;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idCategorie")
    @JoinColumn(name = "id_categorie", referencedColumnName = "id")
    private Categorie categorie;

    public FilmCategorie() {
        this.id = new FilmCategorieId();
    }

    public FilmCategorie(Film film, Categorie categorie) {
        this.id = new FilmCategorieId(film.getId(), categorie.getId());
        this.film = film;
        this.categorie = categorie;
    }

    public FilmCategorie(FilmCategorieId id, Film film, Categorie categorie) {
        this.id = id;
        this.film = film;
        this.categorie = categorie;
    }

    // HELPER
    public void setFilmAndCategory(Film film, Categorie categorie) {
        this.setFilm(film);
        this.setCategorie(categorie);
        if (this.id == null) {
            this.id = new FilmCategorieId();
        }
        this.id.setIdFilm(film.getId());
        this.id.setIdCategorie(categorie.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        FilmCategorie that = (FilmCategorie) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public FilmCategorieId getId() {
        return id;
    }

    public void setId(FilmCategorieId id) {
        this.id = id;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

}
