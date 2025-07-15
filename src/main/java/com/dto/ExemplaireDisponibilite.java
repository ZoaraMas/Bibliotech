package com.dto;

import com.Entite.Exemplaire;

public class ExemplaireDisponibilite {
    private Exemplaire exemplaire;
    private boolean disponible;
    private String message;

    public ExemplaireDisponibilite() {
    }

    public ExemplaireDisponibilite(Exemplaire exemplaire, boolean disponible, String message) {
        this.exemplaire = exemplaire;
        this.disponible = disponible;
        this.message = message;
    }

    public ExemplaireDisponibilite(Exemplaire exemplaire, boolean disponible) {
        this.exemplaire = exemplaire;
        this.disponible = disponible;
        this.message = "";
    }

    public Exemplaire getExemplaire() {
        return exemplaire;
    }

    public void setExemplaire(Exemplaire exemplaire) {
        this.exemplaire = exemplaire;
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

}
