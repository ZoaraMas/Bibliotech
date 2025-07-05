package com.Entite;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.enums.EtatReservationEnum;

@Entity
@Table(name = "etat_reservation")
public class EtatReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reservation", nullable = false)
    private Reservation reservation;

    @Column(name = "date_changement", nullable = false)
    private LocalDateTime dateChangement;

    @Enumerated(EnumType.STRING)
    @Column(name = "etat", nullable = false)
    private EtatReservationEnum etat;

    @Column(name = "commentaire", columnDefinition = "TEXT")
    private String commentaire;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_employe", nullable = false)
    private User employe;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // Constructeurs
    public EtatReservation() {
    }

    public EtatReservation(Reservation reservation, LocalDateTime dateChangement,
            EtatReservationEnum etat, String commentaire, User employe) {
        this.reservation = reservation;
        this.dateChangement = dateChangement;
        this.etat = etat;
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

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public LocalDateTime getDateChangement() {
        return dateChangement;
    }

    public void setDateChangement(LocalDateTime dateChangement) {
        this.dateChangement = dateChangement;
    }

    public EtatReservationEnum getEtat() {
        return etat;
    }

    public void setEtat(EtatReservationEnum etat) {
        this.etat = etat;
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
}