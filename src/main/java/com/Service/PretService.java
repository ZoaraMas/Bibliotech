package com.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.Entite.Livre;
import com.Entite.Pret;
import com.Entite.PretParametreView;
import com.Entite.User;
import com.Entite.Livre;
import com.Repository.LivreRepository;
import com.Repository.PretParametreViewRepository;
import com.Repository.UserRepository;
import com.dto.PenaliteResponse;
import com.Repository.PretRepository;

import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PretService {
    @Autowired
    private PretRepository pretRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private AdherentQuotaService adherentQuotaService;
    @Autowired
    private PretParametreViewService pretParametreViewService;

    // Fonctionalite 1
    // Preter un livre a un membre selon le type d’adherant, le type de livre et la
    // disponibilite du livre en elle meme.
    public void preterUnExemplaireLivre(long idUser, long idEmploye, long idExemplaire) {

    }

    // Verifier si le membre subit une penalite ou non
    // Miverina daoly ny boky zay vo mande ny nombre de jour de penalite
    public PenaliteResponse subitPenalite(Long idInscription) {
        User user = this.userService.findByInscriptionId(idInscription);
        int nombreJourPenaliteTotal = 0;
        // On ajoutera le nombre de jour de penalite total a cette variable en dessous
        LocalDateTime debutPenalite = null; // c'est la derniere la remise en retard la plus recente avant aujourd'hui
        List<PretParametreView> liste = this.pretParametreViewService
                .getAllPretOrderByDateFinAscByIdInscription(idInscription); // obtenir les prets avec la date des
                                                                            // remises trie par datefinPret asc
        LocalDateTime now = LocalDateTime.now();
        for (int i = 0; i < liste.size(); i++) {
            PretParametreView pretParametreDTO = liste.get(i);
            LocalDateTime dateFin = pretParametreDTO.getDateFinPret();
            LocalDateTime dateRemise = pretParametreDTO.getDateRemise();
            if (dateFin.isBefore(now)) {
                if (dateRemise == null) { // livre non rendu encore
                    // nous somme apres la date fin(date fin + 1)
                    return PenaliteResponse.getPenaliteIndeterminee(user);
                    // de penalite apres
                } else if (dateFin.isBefore(dateRemise)) { // on remet le livre apres le deadline
                    debutPenalite = dateRemise;
                    int nombreJourPenalite = pretParametreDTO.getPenaliteJours();
                    nombreJourPenaliteTotal += nombreJourPenalite;
                    // Logique ou on attend pas de rendre tout les livres avant de lancer la
                    // penalite
                    // if(currFinPenalite == null) currFinPenalite =
                    // dateRemise.plusDays(nombreJourPenalite);
                    // else currFinPenalite = currFinPenalite.plusDays(nombreJourPenalite);
                }
            }
        }
        // Arrive a ce stade, tout les livres on ete rendus (ou fin date pret < now())
        // de toutes les penalites successives
        if (debutPenalite != null) {
            LocalDateTime finPenalite = debutPenalite.plusDays(nombreJourPenaliteTotal);
            if (finPenalite.isBefore(now))
                return PenaliteResponse.getNonPenalite(user);
            return PenaliteResponse.getPenaliteDeterminee(user, debutPenalite, finPenalite, nombreJourPenaliteTotal);
        } // si c'est null, a ce stade du code, il n'y a pas de penalite en cours
        return PenaliteResponse.getNonPenalite(user);
    }

    public boolean quotaNonNull(Long idInscription) {
        if (this.getQuotaRestant(idInscription) == 0)
            return false;
        return true;
    }

    public int getQuotaRestant(Long idInscription) {
        return this.adherentQuotaService.getQuotaInscription(idInscription) - this.getNombrePretActuel(idInscription);
    }

    public int getNombrePretActuel(Long idInscription) {
        LocalDateTime dateCible = LocalDateTime.now();
        Integer result = this.pretParametreViewService.getQuotaDepenseActuel(dateCible, idInscription);
        return result;
    }

    public boolean exemplaireEstDisponible(Long idExemplaire) throws Exception {
        LocalDateTime dateCible = LocalDateTime.now();
        PretParametreView pretParametreDTO = this.pretParametreViewService.findPretWhereExemplaireIn(dateCible,
                idExemplaire);
        if (pretParametreDTO == null)
            return true;
        throw new Exception("l'exemplaire de livre " + idExemplaire + "n'est pas encore disponible jusqu'au "
                + pretParametreDTO.getDateFinPret().toString());
    }

    // Verifier si le membre est actuellement inscrit
    public boolean membreEstInscrit() {
        return true;
    }

    public List<Pret> findAll() {
        return this.pretRepository.findAll();
    }
}
