package com.Entite;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "prolongement_pret_view")
@Immutable // Marks this as a read-only entity since it's based on a view
public class ProlongementPretView {
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "id_pret")
    private Long idPret;
    @Column(name = "date_demande")
    private LocalDateTime dateDemande;
    @Column(name = "commentaire")
    private String commentaire;
    @Column(name = "id_employe")
    private Long idEmploye;

    public ProlongementPretView() {
    }

    public ProlongementPretView(Long id, Long idPret, LocalDateTime dateDemande, String commentaire, Long idEmploye) {
        this.id = id;
        this.idPret = idPret;
        this.dateDemande = dateDemande;
        this.commentaire = commentaire;
        this.idEmploye = idEmploye;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPret() {
        return idPret;
    }

    public void setIdPret(Long idPret) {
        this.idPret = idPret;
    }

    public LocalDateTime getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(LocalDateTime dateDemande) {
        this.dateDemande = dateDemande;
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