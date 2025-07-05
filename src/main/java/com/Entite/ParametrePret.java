package com.Entite;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "parametre_pret", 
       uniqueConstraints = @UniqueConstraint(
           name = "unique_parametre", 
           columnNames = {"id_type_adherent", "id_type_pret", "id_genre"}
       ))
public class ParametrePret {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_type_adherent", nullable = false)
    private TypeAdherent typeAdherent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_type_pret", nullable = false)
    private TypePret typePret;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_genre", nullable = false)
    private Genre genre;

    @Column(name = "nb_jour_pret", nullable = false)
    private Integer nbJourPret;

    @Column(name = "penalite_jours")
    private Integer penaliteJours = 0;

    @Column(name = "nb_jours_avant_prolongation")
    private Integer nbJoursAvantProlongation = 3;

    @Column(name = "nb_jours_prolongation")
    private Integer nbJoursProlongation = 7;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Constructeurs
    public ParametrePret() {
    }

    public ParametrePret(TypeAdherent typeAdherent, TypePret typePret, Genre genre, 
                        Integer nbJourPret, Integer penaliteJours, 
                        Integer nbJoursAvantProlongation, Integer nbJoursProlongation) {
        this.typeAdherent = typeAdherent;
        this.typePret = typePret;
        this.genre = genre;
        this.nbJourPret = nbJourPret;
        this.penaliteJours = penaliteJours;
        this.nbJoursAvantProlongation = nbJoursAvantProlongation;
        this.nbJoursProlongation = nbJoursProlongation;
    }

    // Getters et Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TypeAdherent getTypeAdherent() {
        return typeAdherent;
    }

    public void setTypeAdherent(TypeAdherent typeAdherent) {
        this.typeAdherent = typeAdherent;
    }

    public TypePret getTypePret() {
        return typePret;
    }

    public void setTypePret(TypePret typePret) {
        this.typePret = typePret;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (penaliteJours == null) {
            penaliteJours = 0;
        }
        if (nbJoursAvantProlongation == null) {
            nbJoursAvantProlongation = 3;
        }
        if (nbJoursProlongation == null) {
            nbJoursProlongation = 7;
        }
    }
}