package com.Entite;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "exemplaire", indexes = {
        @Index(name = "idx_exemplaire_reference", columnList = "reference")
})
public class Exemplaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_livre", nullable = false, foreignKey = @ForeignKey(name = "FK_exemplaire_livre"))
    private Livre livre;

    @Column(nullable = false, unique = true, length = 50)
    private String reference;

    @Column(name = "date_arrivee", nullable = false)
    private java.time.LocalDate dateArrivee;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // @OneToMany(mappedBy = "exemplaire", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // private List<Pret> prets = new ArrayList<>();

    // Constructeurs
    public Exemplaire() {
    }

    public Exemplaire(Livre livre, String reference, java.time.LocalDate dateArrivee) {
        this.livre = livre;
        this.reference = reference;
        this.dateArrivee = dateArrivee;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Livre getLivre() {
        return livre;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public java.time.LocalDate getDateArrivee() {
        return dateArrivee;
    }

    public void setDateArrivee(java.time.LocalDate dateArrivee) {
        this.dateArrivee = dateArrivee;
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
}