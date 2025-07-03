package com.Entite;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "inscription")
public class Inscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_inscription", nullable = false)
    private java.time.LocalDate dateInscription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false, foreignKey = @ForeignKey(name = "FK_inscription_user")) 
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_type_adherent", nullable = false, foreignKey = @ForeignKey(name = "FK_inscription_type_adherent"))
    private TypeAdherent typeAdherent;

    @Column(name = "duree_mois", nullable = false)
    private Integer dureeMois;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_employe", nullable = false, foreignKey = @ForeignKey(name = "FK_inscription_employe"))
    private User employe;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // @OneToMany(mappedBy = "inscription", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // private List<Pret> prets = new ArrayList<>();

    // @OneToMany(mappedBy = "inscription", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // private List<Reservation> reservations = new ArrayList<>();

    // Constructeurs
    public Inscription() {
    }

    public Inscription(TypeAdherent typeAdherent, Integer dureeMois, User user, User employe) {
        this.typeAdherent = typeAdherent;
        this.dureeMois = dureeMois;
        this.user = user;
        this.employe = employe;
        this.dateInscription = java.time.LocalDate.now();
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public java.time.LocalDate getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(java.time.LocalDate dateInscription) {
        this.dateInscription = dateInscription;
    }

    public TypeAdherent getTypeAdherent() {
        return typeAdherent;
    }

    public void setTypeAdherent(TypeAdherent typeAdherent) {
        this.typeAdherent = typeAdherent;
    }

    public Integer getDureeMois() {
        return dureeMois;
    }

    public void setDureeMois(Integer dureeMois) {
        this.dureeMois = dureeMois;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    // public List<Pret> getPrets() {
    //     return prets;
    // }

    // public void setPrets(List<Pret> prets) {
    //     this.prets = prets;
    // }

    // public List<Reservation> getReservations() {
    //     return reservations;
    // }

    // public void setReservations(List<Reservation> reservations) {
    //     this.reservations = reservations;
    // }
}