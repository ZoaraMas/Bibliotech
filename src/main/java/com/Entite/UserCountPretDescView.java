package com.Entite;

import jakarta.persistence.*;

@Entity()
@Table(name = "user_count_pret_desc")
public class UserCountPretDescView {
    @Id
    private Long id;

    @Column(nullable = false, length = 255)
    private String nom;

    @Column(nullable = false, name = "nombre_pret")
    private Long nombrePret;

    public UserCountPretDescView() {
    }

    public UserCountPretDescView(Long id, String nom, Long nombrePret) {
        this.id = id;
        this.nom = nom;
        this.nombrePret = nombrePret;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Long getNombrePret() {
        return nombrePret;
    }

    public void setNombrePret(Long nombrePret) {
        this.nombrePret = nombrePret;
    }

}