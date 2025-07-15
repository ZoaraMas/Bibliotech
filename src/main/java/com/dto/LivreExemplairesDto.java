package com.dto;

import java.util.List;

import com.Entite.Exemplaire;
import com.Entite.Livre;

public class LivreExemplairesDto {
    private Livre livre;
    private List<ExemplaireDisponibilite> listeExemplaire;

    public LivreExemplairesDto(Livre livre, List<ExemplaireDisponibilite> listeExemplaire) {
        this.livre = livre;
        this.listeExemplaire = listeExemplaire;
    }

    public LivreExemplairesDto() {
    }

    public Livre getLivre() {
        return livre;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    public List<ExemplaireDisponibilite> getListeExemplaire() {
        return listeExemplaire;
    }

    public void setListeExemplaire(List<ExemplaireDisponibilite> listeExemplaire) {
        this.listeExemplaire = listeExemplaire;
    }

}
