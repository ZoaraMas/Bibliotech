package com.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PretParametreDTO {
      private Long id;
    private Long idInscription; // Assuming camelCase for Java fields
    private Long idExemplaire;
    private Long idTypePret;
    private LocalDateTime datePret; // Use LocalDate if it's just date, LocalDateTime if it includes time
    private Long idEmploye;
    private LocalDateTime createdAt; // Assuming LocalDateTime for created_at

    // Columns from 'parametre_pret' (pp.*)
    private Long ppId; // Maps to pp.id
    private Long idTypeAdherent; // No collision with p.id_type_adherent
    private Long ppIdTypePret; // Maps to pp.id_type_pret
    private Long idGenre; // No collision with p.id_genre
    private Integer nbJourPret;
    private Integer nbLivrePretableEnMemeTemps;
    private Integer penaliteJours;
    private Integer nbJoursAvantProlongation;
    private Integer nbJoursProlongation;
    private LocalDateTime ppCreatedAt; // Maps to pp.created_at

    // Calculated column from view
    private LocalDate dateFinPret; // Use LocalDate for dates

    
    public PretParametreDTO(Long id, Long idInscription, Long idExemplaire, Long idTypePret, LocalDateTime datePret,
            Long idEmploye, LocalDateTime createdAt, Long ppId, Long idTypeAdherent, Long ppIdTypePret, Long idGenre,
            Integer nbJourPret, Integer nbLivrePretableEnMemeTemps, Integer penaliteJours,
            Integer nbJoursAvantProlongation, Integer nbJoursProlongation, LocalDateTime ppCreatedAt,
            LocalDate dateFinPret) {
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
        this.nbLivrePretableEnMemeTemps = nbLivrePretableEnMemeTemps;
        this.penaliteJours = penaliteJours;
        this.nbJoursAvantProlongation = nbJoursAvantProlongation;
        this.nbJoursProlongation = nbJoursProlongation;
        this.ppCreatedAt = ppCreatedAt;
        this.dateFinPret = dateFinPret;
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

    public Long getIdTypePret() {
        return idTypePret;
    }

    public void setIdTypePret(Long idTypePret) {
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

    public Long getPpId() {
        return ppId;
    }

    public void setPpId(Long ppId) {
        this.ppId = ppId;
    }

    public Long getIdTypeAdherent() {
        return idTypeAdherent;
    }

    public void setIdTypeAdherent(Long idTypeAdherent) {
        this.idTypeAdherent = idTypeAdherent;
    }

    public Long getPpIdTypePret() {
        return ppIdTypePret;
    }

    public void setPpIdTypePret(Long ppIdTypePret) {
        this.ppIdTypePret = ppIdTypePret;
    }

    public Long getIdGenre() {
        return idGenre;
    }

    public void setIdGenre(Long idGenre) {
        this.idGenre = idGenre;
    }

    public Integer getNbJourPret() {
        return nbJourPret;
    }

    public void setNbJourPret(Integer nbJourPret) {
        this.nbJourPret = nbJourPret;
    }

    public Integer getNbLivrePretableEnMemeTemps() {
        return nbLivrePretableEnMemeTemps;
    }

    public void setNbLivrePretableEnMemeTemps(Integer nbLivrePretableEnMemeTemps) {
        this.nbLivrePretableEnMemeTemps = nbLivrePretableEnMemeTemps;
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

    public LocalDate getDateFinPret() {
        return dateFinPret;
    }

    public void setDateFinPret(LocalDate dateFinPret) {
        this.dateFinPret = dateFinPret;
    }

    
}
