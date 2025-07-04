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
    public boolean subitPenalite(Long idInscription) {
        int nombreJourPenaliteTotal = 0;
        List<PretParametreView> liste = this.pretParametreViewService
                .getAllPretOrderByDateFinAscByIdInscription(idInscription);
        LocalDateTime now = LocalDateTime.now();
        for (int i = 0; i < liste.size(); i++) {
            PretParametreView pretParametreDTO = liste.get(i);
            LocalDateTime dateFin = pretParametreDTO.getDateFinPret();
            LocalDateTime dateRemise = pretParametreDTO.getDateRemise();
            if (dateFin.isBefore(now)) {
                if (dateRemise == null) {
                    // nous somme apres la date fin(date fin + 1)
                    return true; // penalite indetermine encore, rendre le livre pour connaitre le nombre de jour
                                 // de penalite apres
                } else if (dateFin.isBefore(dateRemise)) { // on remet le livre apres le deadline
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
        // Arrive a ce stade, tout les livres on ete rendus, obtenir la date de la fin
        // de toutes les penalites successives
        LocalDateTime finPenalite = liste.get(liste.size() - 1).getDateRemise().plusDays(nombreJourPenaliteTotal);
        if (finPenalite.isBefore(now))
            return false;
        return true;
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
