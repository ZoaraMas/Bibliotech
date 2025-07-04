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
    private Long idTypePret;

    @Column(name = "date_pret")
    private LocalDateTime datePret;

    @Column(name = "id_employe")
    private Long idEmploye;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "pp_id")
    private Long ppId;

    @Column(name = "id_type_adherent")
    private Long idTypeAdherent;

    @Column(name = "pp_id_type_pret")
    private Long ppIdTypePret;

    @Column(name = "id_genre")
    private Long idGenre;

    @Column(name = "nb_jour_pret")
    private Integer nbJourPret;

    @Column(name = "nb_livre_pretable_en_meme_temps")
    private Integer nbLivrePretableEnMemeTemps;

    @Column(name = "penalite_jours")
    private Integer penaliteJours;

    @Column(name = "nb_jours_avant_prolongation")
    private Integer nbJoursAvantProlongation;

    @Column(name = "nb_jours_prolongation")
    private Integer nbJoursProlongation;

    @Column(name = "pp_created_at")
    private LocalDateTime ppCreatedAt;

    @Column(name = "date_fin_pret")
    private LocalDate dateFinPret;

    @Column(name = "date_remise")
    private LocalDate dateRemise;

    // Constructors
    public PretParametreView() {
        // Default constructor
    }

    // Full constructor for convenience
    public PretParametreView(Long id, Long idInscription, Long idExemplaire, Long idTypePret, 
                           LocalDateTime datePret, Long idEmploye, LocalDateTime createdAt,
                           Long ppId, Long idTypeAdherent, Long ppIdTypePret, Long idGenre,
                           Integer nbJourPret, Integer nbLivrePretableEnMemeTemps, Integer penaliteJours,
                           Integer nbJoursAvantProlongation, Integer nbJoursProlongation, 
                           LocalDateTime ppCreatedAt, LocalDate dateFinPret, LocalDate dateRemise) {
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
        this.dateRemise = dateRemise;
    }

    // Getters and setters for all fields
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

    public LocalDate getDateRemise() {
        return dateRemise;
    }

    public void setDateRemise(LocalDate dateRemise) {
        this.dateRemise = dateRemise;
    }

    // Utility methods (similar to your DTO)
    public boolean isBookReturned() {
        return this.dateRemise != null;
    }

    public boolean isOverdue() {
        if (isBookReturned()) {
            return false;
        }
        return LocalDate.now().isAfter(this.dateFinPret);
    }

    public long getDaysOverdue() {
        if (!isOverdue()) {
            return 0;
        }
        return java.time.temporal.ChronoUnit.DAYS.between(this.dateFinPret, LocalDate.now());
    }

    // Conversion method to DTO
    // public PretParametreDTO toDTO() {
    //     return new PretParametreDTO(
    //         this.id,
    //         this.idInscription,
    //         this.idExemplaire,
    //         this.idTypePret,
    //         this.datePret,
    //         this.idEmploye,
    //         this.createdAt,
    //         this.ppId,
    //         this.idTypeAdherent,
    //         this.ppIdTypePret,
    //         this.idGenre,
    //         this.nbJourPret,
    //         this.nbLivrePretableEnMemeTemps,
    //         this.penaliteJours,
    //         this.nbJoursAvantProlongation,
    //         this.nbJoursProlongation,
    //         this.ppCreatedAt,
    //         this.dateFinPret,
    //         this.dateRemise
    //     );
    // }

    // // Static factory method to create from DTO
    // public static PretParametreView fromDTO(PretParametreDTO dto) {
    //     return new PretParametreView(
    //         dto.getId(),
    //         dto.getIdInscription(),
    //         dto.getIdExemplaire(),
    //         dto.getIdTypePret(),
    //         dto.getDatePret(),
    //         dto.getIdEmploye(),
    //         dto.getCreatedAt(),
    //         dto.getPpId(),
    //         dto.getIdTypeAdherent(),
    //         dto.getPpIdTypePret(),
    //         dto.getIdGenre(),
    //         dto.getNbJourPret(),
    //         dto.getNbLivrePretableEnMemeTemps(),
    //         dto.getPenaliteJours(),
    //         dto.getNbJoursAvantProlongation(),
    //         dto.getNbJoursProlongation(),
    //         dto.getPpCreatedAt(),
    //         dto.getDateFinPret(),
    //         dto.getDateRemise()
    //     );
    // }

    // Equals and hashCode based on ID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PretParametreView that = (PretParametreView) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }



    @Override
    public String toString() {
        return "PretParametreView{" +
                "id=" + id +
                ", idInscription=" + idInscription +
                ", idExemplaire=" + idExemplaire +
                ", dateRemise=" + (dateRemise != null ? dateRemise : "NOT_RETURNED") +
                ", isOverdue=" + isOverdue() +
                ", daysOverdue=" + getDaysOverdue() +
                '}';
    }
}