package com.Entite;

import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class FilmCategorieId implements java.io.Serializable {
    private Long idFilm;
    private Long idCategorie;

    public FilmCategorieId() {
    }

    public FilmCategorieId(Long filmId, Long categoryId) {
        this.idFilm = filmId;
        this.idCategorie = categoryId;
    }

    // IMPORTANT: equals() and hashCode() are crucial for composite keys
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        FilmCategorieId that = (FilmCategorieId) o;
        return Objects.equals(idFilm, that.idFilm) &&
                Objects.equals(idCategorie, that.idCategorie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idFilm, idCategorie);
    }

    public Long getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(Long idFilm) {
        this.idFilm = idFilm;
    }

    public Long getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(Long idCategorie) {
        System.out.println("i am long");
        System.out.println(idCategorie);
        System.out.println(idCategorie.getClass().toString());
        this.idCategorie = idCategorie;
    }

}
