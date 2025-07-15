package com.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Entite.Exemplaire;
import com.Entite.Inscription;
import com.Entite.Pret;
import com.Entite.PretParametreView;
import com.Entite.Prolongement;
import com.Entite.Reservation;
import com.Entite.User;
import com.Repository.ProlongementRepository;
import com.dto.PenaliteResponse;

@Service
public class ProlongementService {
    @Autowired
    private ProlongementRepository prolongementRepository;

    @Autowired
    private PretService pretService;
    @Autowired
    private PretParametreViewService pretParametreViewService;
    @Autowired
    private InscriptionService inscriptionService;
    @Autowired
    private UserService userService;

    public void demanderProlongement(Long idPret, LocalDateTime dateDemande, String commentaire, Long idEmploye)
            throws Exception {
        // 1 Le pret existe
        Pret pret = this.pretService.findById(idPret);
        this.pretService.pretExists(idPret); // throw new exception if this goes wrong
        // 2 Le prolongement est valable
        PretParametreView pretParametreView = this.pretParametreViewService.findById(idPret);
        if (!pretParametreView.dateValablePourProlongement(dateDemande))
            throw new Exception("Erreur, vous n'etes pas autorise encore a prolonger");

        // 3 Verifier si un pret n'a pas encore ete prolonge
        if (pretEstDejaProlonge(idPret))
            throw new Exception("Impossible d'effectuer l'operation, Le pret a deja ete prolonge");
        // 4 Verifier si le pret a deja ete rendu
        if (this.pretParametreViewService.pretEstRendu(idPret))
            throw new Exception("Le pret en question a deja ete rendu, impossiblee de poursuivre");
        // L'adherent n'est pas penalise
        // Le membre ne subit pas de penalite
        Inscription currInscription = inscriptionService.getCurrentInscription(pret.getInscription().getUser().getId());
        // Subit une penalite maintenant
        PenaliteResponse penaliteResponse = this.pretService.subitPenalite(currInscription.getId(), dateDemande);
        if (penaliteResponse.isSubitPenalite()) {
            throw new Exception("Vous etes actuellement penalise, " + penaliteResponse.getMessage());
        }
        // l'employe existe
        if (!userService.userExists(idEmploye)) {
            throw new Exception("L'utilisateur employe ID:" + idEmploye + " n'existe pas");
        }
        User employe = this.userService.findById(idEmploye);
        Prolongement prolongement = new Prolongement(pret, dateDemande, commentaire, employe);
        this.prolongementRepository.save(prolongement);
    }

    // Verifier si un pret n'a pas encore ete prolonge
    public boolean pretEstDejaProlonge(Long idPret) {
        Prolongement prolongement = this.prolongementRepository.findByPretId(idPret).orElse(null);
        if (prolongement == null)
            return false;
        return true;
    }

    public Prolongement findByIdWithAllRelations(long idProlongement) {
        Optional<Prolongement> prolongement = prolongementRepository.findByIdWithAllRelations(idProlongement);
        if (prolongement.isPresent()) {
            return prolongement.get();
        } else {
            throw new RuntimeException("Prolongation avec ID: " + idProlongement + " n'existe pas");
        }
    }

    public boolean prolongementExiste(Long idProlongement) {
        return this.prolongementRepository.existsById(idProlongement);
    }

}
