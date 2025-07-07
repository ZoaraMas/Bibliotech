package com.dto;

public class LivreNombre {
    private Integer id;
    private String titre;
    private Long nombre;

    public LivreNombre(Integer id, String titre, Long nombre) {
        this.id = id;
        this.titre = titre;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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