package com.dto;

import java.time.LocalDate;

import com.Entite.Exemplaire;

public class ExemplaireDisponibilite {
    private Long id;
    private String reference;
    private LocalDate dateArrivee;
    private boolean disponible;
    private String message;

    public ExemplaireDisponibilite() {
    }

    
    public ExemplaireDisponibilite(Long id, String reference, LocalDate dateArrivee) {
        this.id = id;
        this.reference = reference;
        this.dateArrivee = dateArrivee;
    }


    public ExemplaireDisponibilite(Long id, String reference, LocalDate dateArrivee, boolean disponible,
            String message) {
        this.id = id;
        this.reference = reference;
        this.dateArrivee = dateArrivee;
        this.disponible = disponible;
        this.message = message;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public LocalDate getDateArrivee() {
        return dateArrivee;
    }

    public void setDateArrivee(LocalDate dateArrivee) {
        this.dateArrivee = dateArrivee;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

}
