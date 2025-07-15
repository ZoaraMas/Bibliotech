package com.Entite;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.enums.EtatReservationEnum;

@Entity
@Table(name = "etat_prolongement_pret")
public class EtatProlongement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_prolongement_pret", nullable = false)
    private Prolongement prolongement;

    @Column(name = "date_validation", nullable = false)
    private LocalDateTime dateValidation;

    @Enumerated(EnumType.STRING)
    @Column(name = "etat", nullable = false)
    private EtatReservationEnum etat;

    @Column(name = "commentaire", columnDefinition = "TEXT")
    private String commentaire;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_employe", nullable = false)
    private User employe;

    public EtatProlongement() {
    }

    public EtatProlongement(Prolongement prolongement, LocalDateTime dateValidation, EtatReservationEnum etat,
            String commentaire, User employe) {
        this.prolongement = prolongement;
        this.dateValidation = dateValidation;
        this.etat = etat;
        this.commentaire = commentaire;
        this.employe = employe;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Prolongement getProlongement() {
        return prolongement;
    }

    public void setProlongement(Prolongement prolongement) {
        this.prolongement = prolongement;
    }

    public LocalDateTime getDateValidation() {
        return dateValidation;
    }

    public void setDateValidation(LocalDateTime dateValidation) {
        this.dateValidation = dateValidation;
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

}