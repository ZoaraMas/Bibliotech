package com.dto;

public class LivreNombre {
    private Long id;
    private String titre;
    private Long nombre;

    public LivreNombre(Long id, String titre, Long nombre) {
        this.id = id;
        this.titre = titre;
        this.nombre = nombre;
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

    public Long getNombre() {
        return nombre;
    }

    public void setNombre(Long nombre) {
        this.nombre = nombre;
    }

}