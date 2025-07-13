package com.Entite;

import jakarta.persistence.*;

@Entity()
@Table(name = "pret_nombre_desc")
public class PretNombreDescView {
    @Id
    private Long id;

    @Column(nullable = false, length = 255)
    private String titre;

    @Column(nullable = false, name = "nombre_pret")
    private Long nombrePret;

    public PretNombreDescView() {
    }

    public PretNombreDescView(Long id, String titre, Long nombre) {
        this.id = id;
        this.titre = titre;
        this.nombrePret = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Long getNombrePret() {
        return nombrePret;
    }

    public void setNombrePret(Long nombrePret) {
        this.nombrePret = nombrePret;
    }
}