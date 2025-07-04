package com.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.Entite.User;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Classe de réponse pour les informations de pénalité
 */
public class PenaliteResponse {

    private boolean subitPenalite;
    private String message;
    private int nombreJoursPenaliteTotal;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime debutPenalite;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime finPenalite;

    public PenaliteResponse(User user) {
        this.message = "Aucune penalite en cours pour " + user.getNom();
        this.subitPenalite = false;
        this.nombreJoursPenaliteTotal = 0;
        this.debutPenalite = null;
        this.finPenalite = null;
    }

    // Toute les livres on deja ete rendu, et on peut connaitre quand la penalite va
    // se terminer
    public static PenaliteResponse getPenaliteDeterminee(User user, LocalDateTime debutPenalite,
            LocalDateTime finPenalite, int nombreJourPenalite) {
        PenaliteResponse result = new PenaliteResponse(user);
        result.setMessage(user.getNom() + " est penalise entre jusqu'a " + finPenalite);
        result.setSubitPenalite(true);
        result.setDebutPenalite(debutPenalite);
        result.setFinPenalite(finPenalite);
        result.setNombreJoursPenaliteTotal(nombreJourPenalite);
        return result;
    }

    public static PenaliteResponse getPenaliteIndeterminee(User user) {
        PenaliteResponse result = new PenaliteResponse(user);
        result.setMessage(user.getNom() + " est penalise indefiniment, veuillez rendre tout les livres d'abord");
        result.setSubitPenalite(true);
        return result;
    }

    public static PenaliteResponse getNonPenalite(User user) {
        return new PenaliteResponse(user);
    }

    // Énumération pour les types de pénalité
    public enum TypePenalite {
        AUCUNE("Aucune pénalité"),
        EN_COURS("Pénalité en cours"),
        LIVRE_NON_RENDU("Livre(s) non rendu(s) - pénalité indéterminée"),
        PENALITE_TERMINEE("Pénalité terminée");

        private final String description;

        TypePenalite(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    public boolean isSubitPenalite() {
        return subitPenalite;
    }

    public void setSubitPenalite(boolean subitPenalite) {
        this.subitPenalite = subitPenalite;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getNombreJoursPenaliteTotal() {
        return nombreJoursPenaliteTotal;
    }

    public void setNombreJoursPenaliteTotal(int nombreJoursPenaliteTotal) {
        this.nombreJoursPenaliteTotal = nombreJoursPenaliteTotal;
    }

    public LocalDateTime getDebutPenalite() {
        return debutPenalite;
    }

    public void setDebutPenalite(LocalDateTime debutPenalite) {
        this.debutPenalite = debutPenalite;
    }

    public LocalDateTime getFinPenalite() {
        return finPenalite;
    }

    public void setFinPenalite(LocalDateTime finPenalite) {
        this.finPenalite = finPenalite;
    }

}