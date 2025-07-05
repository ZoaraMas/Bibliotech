package com.Entite;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_inscription", nullable = false)
    private Inscription inscription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_exemplaire", nullable = false)
    private Exemplaire exemplaire;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_type_pret", nullable = false)
    private TypePret typePret;

    @Column(name = "date_reservation", nullable = false)
    private LocalDateTime dateReservation;

    @Column(name = "commentaire", columnDefinition = "TEXT")
    private String commentaire;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_employe", nullable = false)
    private User employe;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // Relation bidirectionnelle avec EtatReservation
    // @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, fetch =
    // FetchType.LAZY)
    // private List<EtatReservation> etatsReservation = new ArrayList<>();

    // Constructeurs
    public Reservation() {
    }

    public Reservation(Inscription inscription, Exemplaire exemplaire, TypePret typePret,
            LocalDateTime dateReservation, String commentaire, User employe) {
        this.inscription = inscription;
        this.exemplaire = exemplaire;
        this.typePret = typePret;
        this.dateReservation = dateReservation;
        this.commentaire = commentaire;
        this.employe = employe;
        this.createdAt = LocalDateTime.now();
    }

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Inscription getInscription() {
        return inscription;
    }

    public void setInscription(Inscription inscription) {
        this.inscription = inscription;
    }

    public Exemplaire getExemplaire() {
        return exemplaire;
    }

    public void setExemplaire(Exemplaire exemplaire) {
        this.exemplaire = exemplaire;
    }

    public TypePret getTypePret() {
        return typePret;
    }

    public void setTypePret(TypePret typePret) {
        this.typePret = typePret;
    }

    public LocalDateTime getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(LocalDateTime dateReservation) {
        this.dateReservation = dateReservation;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public User getEmploye() {
        return employe;
    }

    public void setEmploye(User employe) {
        this.employe = employe;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // public List<EtatReservation> getEtatsReservation() {
    // return etatsReservation;
    // }

    // public void setEtatsReservation(List<EtatReservation> etatsReservation) {
    // this.etatsReservation = etatsReservation;
    // }

    // // Méthodes utilitaires
    // public void ajouterEtatReservation(EtatReservation etatReservation) {
    // etatsReservation.add(etatReservation);
    // etatReservation.setReservation(this);
    // }

    // public void retirerEtatReservation(EtatReservation etatReservation) {
    // etatsReservation.remove(etatReservation);
    // etatReservation.setReservation(null);
    // }

    // Méthode pour obtenir l'état actuel de la réservation
    // public EtatReservation getEtatActuel() {
    // return etatsReservation.stream()
    // .max((e1, e2) -> e1.getDateChangement().compareTo(e2.getDateChangement()))
    // .orElse(null);
    // }
}