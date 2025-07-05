package com.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.Entite.Exemplaire;
import com.Entite.Inscription;
import com.Entite.Livre;
import com.Entite.Pret;
import com.Entite.PretParametreView;
import com.Entite.TypePret;
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
    @Autowired
    private ExemplaireService exemplaireService;
    @Autowired
    private InscriptionService inscriptionService;
    @Autowired
    private TypePretService typePretService;

    // Obtenir le pret d'un exemplaire qui est en cours
    public Pret getPretFromExemplaireActuel(Long idExemplaire) throws Exception {
        PretParametreView temp = this.pretParametreViewService.getCurrPretWithIdExemplaire(idExemplaire);
        Pret pret = this.pretRepository.findById(temp.getId()).orElseThrow(() -> new Exception(
                "L'id exemplaire ne semble pas etre prete en ce moment, veuillez contacter l'admin"));
        return pret;
    }

    // Fonctionalite 1
    // Preter un livre a un membre selon le type d’adherant, le type de livre et la
    // disponibilite du livre en elle meme.
    public void preterUnExemplaireLivre(long idUser, long idEmploye, long idExemplaire, Integer idTypePret)
            throws Exception {
        // le membre existe
        if (!userService.userExists(idUser)) {
            throw new Exception("L'utilisateur ID:" + idUser + " n'existe pas");
        }
        // l’exemplaire du livre existe
        if (!exemplaireService.exemplaireExists(idExemplaire)) {
            throw new Exception("L'exemplaire de livre ID:" + idExemplaire + " n'existe pas");
        }
        // Exemplaire disponible
        if (!exemplaireService.exemplaireDisponible(idExemplaire)) {
            throw new Exception("L'exemplaire de livre ID:" + idExemplaire + " n'est pas encore disponible");
        }

        // Membre actuellement inscrit
        if (!inscriptionService.estActuellementInscrit(idUser)) {
            throw new Exception("Actuellement l'utilisateur ID:" + idUser + " n'est pas inscrit");
        }

        // Le membre ne subit pas de penalite
        Inscription currInscription = inscriptionService.getCurrentInscription(idUser);
        PenaliteResponse penaliteResponse = this.subitPenalite(currInscription.getId());
        if (penaliteResponse.isSubitPenalite()) {
            throw new Exception(penaliteResponse.getMessage());
        }

        TypePret typePret = this.typePretService.findById(idTypePret);
        // Le membre n’a pas encore termine tout son quota
        // Si il prend sur place, la regle ne s'applique pas
        if (!this.quotaNonNull(currInscription.getId()) && idTypePret != 2) {
            throw new Exception("Quota insuffisant, veuillez rendre au moins un livre d'abord.");
        }
        // l'employe existe
        if (!userService.userExists(idEmploye)) {
            throw new Exception("L'utilisateur employe ID:" + idEmploye + " n'existe pas");
        }
        // l'exemplaiire existe
        Exemplaire exemplaire = this.exemplaireService.findById(idExemplaire);

        // typePret existe

        LocalDateTime now = LocalDateTime.now();
        User employe = this.userService.findById(idEmploye);
        Pret pret = new Pret(currInscription, exemplaire, typePret, now, employe);
        this.pretRepository.save(pret);
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

    public int quotaNonNullFromUserId(Long idUser) throws Exception {
        Inscription currInscription = inscriptionService.getCurrentInscription(idUser);
        return this.getQuotaRestant(currInscription.getId());
    }

    public boolean quotaNonNull(Long idInscription) {
        if (this.getQuotaRestant(idInscription) <= 0)
            return false;
        return true;
    }

    public boolean quotaNonNull(Long idInscription, LocalDateTime dateCible) {
        if (this.getQuotaRestant(idInscription, dateCible) <= 0)
            return false;
        return true;
    }

    public int getQuotaRestant(Long idInscription) {
        LocalDateTime dateCible = LocalDateTime.now();
        return this.getQuotaRestant(idInscription, dateCible);
    }

    public int getQuotaRestant(Long idInscription, LocalDateTime dateCible) {
        return this.adherentQuotaService.getQuotaInscription(idInscription)
                - this.getNombrePretActuel(idInscription, dateCible);
    }

    public int getNombrePretActuel(Long idInscription, LocalDateTime dateCible) {
        Integer result = this.pretParametreViewService.getQuotaDepenseActuel(dateCible, idInscription);
        return result;
    }

    public boolean exemplaireEstDisponible(Long idExemplaire) throws Exception {
        LocalDateTime dateCible = LocalDateTime.now();
        return this.exemplaireEstDisponible(idExemplaire, dateCible);
    }

    public boolean exemplaireEstDisponible(Long idExemplaire, LocalDateTime dateCible) throws Exception {
        PretParametreView pretParametreDTO = this.pretParametreViewService.findPretWhereExemplaireIn(dateCible,
                idExemplaire);
        if (pretParametreDTO == null)
            return true;
        throw new Exception("l'exemplaire de livre " + idExemplaire + "n'est pas encore disponible jusqu'au "
                + pretParametreDTO.getDateFinPret().toString());
    }

    public boolean exemplaireEstNonDisponible(Long idExemplaire) throws Exception {
        LocalDateTime dateCible = LocalDateTime.now();
        return this.exemplaireEstNonDisponible(idExemplaire, dateCible);
    }

    public boolean exemplaireEstNonDisponible(Long idExemplaire, LocalDateTime dateCible) throws Exception {
        PretParametreView pretParametreDTO = this.pretParametreViewService.findPretWhereExemplaireIn(dateCible,
                idExemplaire);
        if (pretParametreDTO == null)
            throw new Exception("l'exemplaire de livre " + idExemplaire + "est toujours disponible");
        return true;
    }

    // Verifier si le membre est actuellement inscrit
    public boolean membreEstInscrit() {
        return true;
    }

    public List<Pret> findAll() {
        return this.pretRepository.findAll();
    }
}
