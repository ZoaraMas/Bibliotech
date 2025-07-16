package com.Entite;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "pret_parametre")
@Immutable // Marks this as a read-only entity since it's based on a view
public class PretParametreView {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "id_inscription")
    private Long idInscription;

    @Column(name = "id_exemplaire")
    private Long idExemplaire;

    @Column(name = "id_type_pret")
    private Integer idTypePret;

    @Column(name = "date_pret")
    private LocalDateTime datePret;

    @Column(name = "id_employe")
    private Long idEmploye;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "pp_id")
    private Integer ppId;

    @Column(name = "id_type_adherent")
    private Integer idTypeAdherent;

    @Column(name = "pp_id_type_pret")
    private Integer ppIdTypePret;

    @Column(name = "id_genre")
    private Integer idGenre;

    @Column(name = "nb_jour_pret")
    private Integer nbJourPret;

    @Column(name = "penalite_jours")
    private Integer penaliteJours;

    @Column(name = "nb_jours_avant_prolongation")
    private Integer nbJoursAvantProlongation;

    @Column(name = "nb_jours_prolongation")
    private Integer nbJoursProlongation;

    @Column(name = "pp_created_at")
    private LocalDateTime ppCreatedAt;

    @Column(name = "date_fin_pret")
    private LocalDateTime dateFinPret;

    @Column(name = "date_remise")
    private LocalDateTime dateRemise;

    // Constructors
    public PretParametreView() {
        // Default constructor
    }

    public PretParametreView(Long id, Long idInscription, Long idExemplaire, Integer idTypePret, LocalDateTime datePret,
            Long idEmploye, LocalDateTime createdAt, Integer ppId, Integer idTypeAdherent, Integer ppIdTypePret,
            Integer idGenre, Integer nbJourPret, Integer penaliteJours,
            Integer nbJoursAvantProlongation, Integer nbJoursProlongation, LocalDateTime ppCreatedAt,
            LocalDateTime dateFinPret, LocalDateTime dateRemise) {
        this.id = id;
        this.idInscription = idInscription;
        this.idExemplaire = idExemplaire;
        this.idTypePret = idTypePret;
        this.datePret = datePret;
        this.idEmploye = idEmploye;
        this.createdAt = createdAt;
        this.ppId = ppId;
        this.idTypeAdherent = idTypeAdherent;
        this.ppIdTypePret = ppIdTypePret;
        this.idGenre = idGenre;
        this.nbJourPret = nbJourPret;
        this.penaliteJours = penaliteJours;
        this.nbJoursAvantProlongation = nbJoursAvantProlongation;
        this.nbJoursProlongation = nbJoursProlongation;
        this.ppCreatedAt = ppCreatedAt;
        this.dateFinPret = dateFinPret;
        this.dateRemise = dateRemise;
    }

    // Verifier si une date est valable pour la prolongement de ce pret
    public boolean dateValablePourProlongement(LocalDateTime dateDemande) throws Exception {
        LocalDateTime debutPret = this.getDatePret();
        LocalDateTime finPret = this.getDateFinPret();
        if (dateDemande.isAfter(finPret))
            throw new Exception(
                    "La date de demande de prolongation se situe apres la fin prevue pour le pret en question");
        if (dateDemande.isBefore(debutPret))
            throw new Exception(
                    "La date de demande de prolongation se situe avant le debut prevue pour le pret en question");
        LocalDateTime dateProlongationValable = finPret.minusDays(this.getNbJoursAvantProlongation());
        if (dateDemande.isBefore(dateProlongationValable))
            throw new Exception("Vous devez attendre le " + dateProlongationValable
                    + " avant de pouvoir demander a prolonger le pret");
        return true;
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

    public LocalDateTime getDatePret() {
        return datePret;
    }

    public void setDatePret(LocalDateTime datePret) {
        this.datePret = datePret;
    }

    public Long getIdEmploye() {
        return idEmploye;
    }

    public void setIdEmploye(Long idEmploye) {
        this.idEmploye = idEmploye;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getPpId() {
        return ppId;
    }

    public void setPpId(Integer ppId) {
        this.ppId = ppId;
    }

    public Integer getIdTypeAdherent() {
        return idTypeAdherent;
    }

    public void setIdTypeAdherent(Integer idTypeAdherent) {
        this.idTypeAdherent = idTypeAdherent;
    }

    public Integer getPpIdTypePret() {
        return ppIdTypePret;
    }

    public void setPpIdTypePret(Integer ppIdTypePret) {
        this.ppIdTypePret = ppIdTypePret;
    }

    public Integer getIdGenre() {
        return idGenre;
    }

    public void setIdGenre(Integer idGenre) {
        this.idGenre = idGenre;
    }

    public Integer getNbJourPret() {
        return nbJourPret;
    }

    public void setNbJourPret(Integer nbJourPret) {
        this.nbJourPret = nbJourPret;
    }

    public Integer getPenaliteJours() {
        return penaliteJours;
    }

    public void setPenaliteJours(Integer penaliteJours) {
        this.penaliteJours = penaliteJours;
    }

    public Integer getNbJoursAvantProlongation() {
        return nbJoursAvantProlongation;
    }

    public void setNbJoursAvantProlongation(Integer nbJoursAvantProlongation) {
        this.nbJoursAvantProlongation = nbJoursAvantProlongation;
    }

    public Integer getNbJoursProlongation() {
        return nbJoursProlongation;
    }

    public void setNbJoursProlongation(Integer nbJoursProlongation) {
        this.nbJoursProlongation = nbJoursProlongation;
    }

    public LocalDateTime getPpCreatedAt() {
        return ppCreatedAt;
    }

    public void setPpCreatedAt(LocalDateTime ppCreatedAt) {
        this.ppCreatedAt = ppCreatedAt;
    }

    public LocalDateTime getDateFinPret() {
        return dateFinPret;
    }

    public void setDateFinPret(LocalDateTime dateFinPret) {
        this.dateFinPret = dateFinPret;
    }

    public LocalDateTime getDateRemise() {
        return dateRemise;
    }

    public void setDateRemise(LocalDateTime dateRemise) {
        this.dateRemise = dateRemise;
    }

}