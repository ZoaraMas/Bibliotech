package com.Entite;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "reservation_view")
@Immutable // Marks this as a read-only entity since it's based on a view
public class ReservationView {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "id_inscription")
    private Long idInscription;

    @Column(name = "id_exemplaire")
    private Long idExemplaire;

    @Column(name = "id_type_pret")
    private Integer idTypePret;

    @Column(name = "date_reservation")
    private LocalDateTime dateReservation;

    @Column(name = "commentaire")
    private String commentaire;

    @Column(name = "id_employe")
    private Long idEmploye;

    public ReservationView() {
    }

    public ReservationView(Long id, Long idInscription, Long idExemplaire, Integer idTypePret,
            LocalDateTime dateReservation, String commentaire, Long idEmploye) {
        this.id = id;
        this.idInscription = idInscription;
        this.idExemplaire = idExemplaire;
        this.idTypePret = idTypePret;
        this.dateReservation = dateReservation;
        this.commentaire = commentaire;
        this.idEmploye = idEmploye;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdInscription() {
        return idInscription;
    }

    public void setIdInscription(Long idInscription) {
        this.idInscription = idInscription;
    }

    public Long getIdExemplaire() {
        return idExemplaire;
    }

    public void setIdExemplaire(Long idExemplaire) {
        this.idExemplaire = idExemplaire;
    }

    public Integer getIdTypePret() {
        return idTypePret;
    }

    public void setIdTypePret(Integer idTypePret) {
        this.idTypePret = idTypePret;
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

    public Long getIdEmploye() {
        return idEmploye;
    }

    public void setIdEmploye(Long idEmploye) {
        this.idEmploye = idEmploye;
    }

}