package com.Entite;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "livre", indexes = {
        @Index(name = "idx_livre_titre", columnList = "titre"),
        @Index(name = "idx_livre_auteur", columnList = "auteur"),
        @Index(name = "idx_livre_isbn", columnList = "isbn")
})
public class Livre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String titre;

    @Column(nullable = false, length = 255)
    private String auteur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_genre", nullable = false, foreignKey = @ForeignKey(name = "FK_livre_genre"))
    private Genre genre;

    @Column(unique = true, length = 20)
    private String isbn;

    @Column(length = 100)
    private String edition;

    @Column(name = "nb_page")
    private Integer nbPage;

    @Column(columnDefinition = "TEXT")
    private String resume;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // @OneToMany(mappedBy = "livre", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // private List<Exemplaire> exemplaires = new ArrayList<>();

    // @OneToMany(mappedBy = "livre", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // private List<Reservation> reservations = new ArrayList<>();

    // // Relation Many-to-Many avec Auteur (nouvelle table associative)
    // @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    // @JoinTable(name = "livre_auteur", joinColumns = @JoinColumn(name = "livre_id"), inverseJoinColumns = @JoinColumn(name = "auteur_id"))
    // private List<Auteur> auteurs = new ArrayList<>();

    // Constructeurs
    public Livre() {
    }

    public Livre(String titre, String auteur, Genre genre) {
        this.titre = titre;
        this.auteur = auteur;
        this.genre = genre;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public Integer getNbPage() {
        return nbPage;
    }

    public void setNbPage(Integer nbPage) {
        this.nbPage = nbPage;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // public List<Exemplaire> getExemplaires() {
    //     return exemplaires;
    // }

    // public void setExemplaires(List<Exemplaire> exemplaires) {
    //     this.exemplaires = exemplaires;
    // }

    // public List<Reservation> getReservations() {
    //     return reservations;
    // }

    // public void setReservations(List<Reservation> reservations) {
    //     this.reservations = reservations;
    // }

    // public List<Auteur> getAuteurs() {
    //     return auteurs;
    // }

    // public void setAuteurs(List<Auteur> auteurs) {
    //     this.auteurs = auteurs;
    // }
}