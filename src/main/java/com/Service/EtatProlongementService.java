package com.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.Controller.EtatReservationController;
import com.Entite.EtatProlongement;
import com.Entite.EtatReservation;
import com.Entite.Exemplaire;
import com.Entite.Genre;
import com.Entite.Inscription;
import com.Entite.Livre;
import com.Entite.Pret;
import com.Entite.PretParametreView;
import com.Entite.Prolongement;
import com.Entite.RemiseLivre;
import com.Entite.TypePret;
import com.Entite.User;
import com.Entite.Reservation;
import com.Repository.EtatProlongementRepository;
import com.Repository.EtatReservationRepository;
import com.Repository.LivreRepository;
import com.Repository.PretParametreViewRepository;
import com.Repository.UserRepository;
import com.Utility.MyDate;
import com.dto.PenaliteResponse;
import com.enums.EtatReservationEnum;
import com.Repository.PretRepository;
import com.Repository.RemiseLivreRepository;
import com.Repository.ReservationRepository;

import jakarta.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EtatProlongementService {
    @Autowired
    private EtatProlongementRepository etatProlongementRepository;
    @Autowired
    private ProlongementService prolongementService;
    @Autowired
    private UserService userService;
    @Autowired
    private PretService pretService;
    @Autowired
    private PretParametreViewService pretParametreViewService;
    @Autowired
    private RemiseLivreService remiseLivreService;
    @Autowired
    private EntityManager entityManager;

    // @Transactional
    // public void preparerAjouterEtatProlongement(Long idEmp, Long idProlongement,
    // Boolean confirmer, String commentaire)
    // throws Exception {

    // }

    @Transactional
    public void ajouterEtatProlongement(Long idEmp, Long idProlongement, Boolean confirmer, String commentaire)
            throws Exception {
        // l'employe existe
        if (!userService.userExists(idEmp)) {
            throw new IllegalArgumentException("L'utilisateur employe ID:" + idEmp + " n'existe pas");
        }
        // la prolongation existe
        if (!this.prolongementService.prolongementExiste(idProlongement)) {
            throw new IllegalArgumentException("Le prolongement de pret avec l'ID:" + idProlongement + " n'existe pas");
        }
        // l’etat change du dernier etat sur la prolongation
        if (!etatProlongementChange(idProlongement, confirmer))
            if (confirmer)
                throw new IllegalArgumentException("Le prolongement :" + idProlongement + " a deja ete confirme");
            else
                throw new IllegalArgumentException("Le prolongement :" + idProlongement + " a deja ete refuse");
        User employe = this.userService.findById(idEmp);
        // Maintenant on peut s'occuper de l'acceptation/refus
        // Si refus, refuser tout simplement
        Prolongement prolongement = prolongementService.findByIdWithAllRelations(idProlongement);
        LocalDateTime dateValidation = LocalDateTime.now();
        EtatReservationEnum etat = EtatReservationEnum.REFUSEE;
        EtatProlongement etatProlongement = new EtatProlongement(prolongement, dateValidation, etat, commentaire,
                employe);
        if (!confirmer) {
            this.etatProlongementRepository.save(etatProlongement);
            // this.etatReservationRepository.save(etatReservation);
        } else {
            // throw new Exception(
            // "Desole, les validations de prolongement ne sont pas encore disponible pour
            // l'instant.");
            // Validation requiert les regles de gestions de pret + date choisie
            // On prepare les argumens propers a prets
            PretParametreView pretAProlonger = this.pretParametreViewService.findById(prolongement.getPret().getId());
            LocalDateTime dateRemise = pretAProlonger.getDateFinPret().minusMinutes(1);
            LocalDateTime dateDebut = pretAProlonger.getDateFinPret();
            dateDebut = dateDebut.plusMinutes(5);
            long idUser = prolongement.getPret().getInscription().getUser().getId();
            long idExemplaire = prolongement.getPret().getExemplaire().getId();
            Integer idTypePret = prolongement.getPret().getTypePret().getId();
            // On effectue direct la remise avant la date fin pour eviter la penalisation
            this.remiseLivreService.remettreUnExemplaireDeLivreAvecNouvelleTransaction(idExemplaire, idEmp, dateRemise,
                    "Remise = " + commentaire);
            entityManager.flush();

            // On cree le pret a la date
            this.pretService.preterUnExemplaireLivrePourProlongement(idUser, idEmp, idExemplaire, idTypePret,
                    dateDebut);
            etatProlongement.setEtat(EtatReservationEnum.VALIDEE);
            this.etatProlongementRepository.save(etatProlongement);
        }
    }

    @Transactional
    public void preparerAjouterEtatProlongement(Long idEmp, Long idProlongement, Boolean confirmer, String commentaire)
            throws Exception {
        // l'employe existe
        if (!userService.userExists(idEmp)) {
            throw new IllegalArgumentException("L'utilisateur employe ID:" + idEmp + " n'existe pas");
        }
        // la prolongation existe
        if (!this.prolongementService.prolongementExiste(idProlongement)) {
            throw new IllegalArgumentException("Le prolongement de pret avec l'ID:" + idProlongement + " n'existe pas");
        }
        // l’etat change du dernier etat sur la prolongation
        if (!etatProlongementChange(idProlongement, confirmer))
            if (confirmer)
                throw new IllegalArgumentException("Le prolongement :" + idProlongement + " a deja ete confirme");
            else
                throw new IllegalArgumentException("Le prolongement :" + idProlongement + " a deja ete refuse");
        User employe = this.userService.findById(idEmp);
        // Maintenant on peut s'occuper de l'acceptation/refus
        // Si refus, refuser tout simplement
        Prolongement prolongement = prolongementService.findByIdWithAllRelations(idProlongement);
        LocalDateTime dateValidation = LocalDateTime.now();
        EtatReservationEnum etat = EtatReservationEnum.REFUSEE;
        EtatProlongement etatProlongement = new EtatProlongement(prolongement, dateValidation, etat, commentaire,
                employe);
        if (!confirmer) {
            this.etatProlongementRepository.save(etatProlongement);
            // this.etatReservationRepository.save(etatReservation);
        } else {
            // throw new Exception(
            // "Desole, les validations de prolongement ne sont pas encore disponible pour
            // l'instant.");
            // Validation requiert les regles de gestions de pret + date choisie
            // On prepare les argumens propers a prets
            PretParametreView pretAProlonger = this.pretParametreViewService.findById(prolongement.getPret().getId());
            LocalDateTime dateRemise = pretAProlonger.getDateFinPret().minusMinutes(1);
            LocalDateTime dateDebut = pretAProlonger.getDateFinPret();
            dateDebut = dateDebut.plusMinutes(5);
            long idUser = prolongement.getPret().getInscription().getUser().getId();
            long idExemplaire = prolongement.getPret().getExemplaire().getId();
            Integer idTypePret = prolongement.getPret().getTypePret().getId();
            // On effectue direct la remise avant la date fin pour eviter la penalisation
            this.remiseLivreService.remettreUnExemplaireDeLivreAvecNouvelleTransaction(idExemplaire, idEmp, dateRemise,
                    "Remise = " + commentaire);
            entityManager.flush();
        }
    }

    @Transactional
    public void ajouterEtatProlongementFinal(Long idEmp, Long idProlongement, Boolean confirmer, String commentaire)
            throws Exception {
        // l'employe existe
        if (!userService.userExists(idEmp)) {
            throw new IllegalArgumentException("L'utilisateur employe ID:" + idEmp + " n'existe pas");
        }
        // la prolongation existe
        if (!this.prolongementService.prolongementExiste(idProlongement)) {
            throw new IllegalArgumentException("Le prolongement de pret avec l'ID:" + idProlongement + " n'existe pas");
        }
        // l’etat change du dernier etat sur la prolongation
        if (!etatProlongementChange(idProlongement, confirmer))
            if (confirmer)
                throw new IllegalArgumentException("Le prolongement :" + idProlongement + " a deja ete confirme");
            else
                throw new IllegalArgumentException("Le prolongement :" + idProlongement + " a deja ete refuse");
        User employe = this.userService.findById(idEmp);
        // Maintenant on peut s'occuper de l'acceptation/refus
        // Si refus, refuser tout simplement
        Prolongement prolongement = prolongementService.findByIdWithAllRelations(idProlongement);
        LocalDateTime dateValidation = LocalDateTime.now();
        EtatReservationEnum etat = EtatReservationEnum.REFUSEE;
        EtatProlongement etatProlongement = new EtatProlongement(prolongement, dateValidation, etat, commentaire,
                employe);
        if (!confirmer) {
            this.etatProlongementRepository.save(etatProlongement);
            // this.etatReservationRepository.save(etatReservation);
        } else {
            // throw new Exception(
            // "Desole, les validations de prolongement ne sont pas encore disponible pour
            // l'instant.");
            // Validation requiert les regles de gestions de pret + date choisie
            // On prepare les argumens propers a prets
            PretParametreView pretAProlonger = this.pretParametreViewService.findById(prolongement.getPret().getId());
            LocalDateTime dateRemise = pretAProlonger.getDateFinPret().minusMinutes(1);
            LocalDateTime dateDebut = pretAProlonger.getDateFinPret();
            dateDebut = dateDebut.plusMinutes(5);
            long idUser = prolongement.getPret().getInscription().getUser().getId();
            long idExemplaire = prolongement.getPret().getExemplaire().getId();
            Integer idTypePret = prolongement.getPret().getTypePret().getId();
            // On effectue direct la remise avant la date fin pour eviter la penalisation
            // this.remiseLivreService.remettreUnExemplaireDeLivreAvecNouvelleTransaction(idExemplaire,
            // idEmp, dateRemise,
            // "Remise = " + commentaire);
            // entityManager.flush();

            // On cree le pret a la date
            this.pretService.preterUnExemplaireLivrePourProlongement(idUser, idEmp, idExemplaire, idTypePret,
                    dateDebut);
            etatProlongement.setEtat(EtatReservationEnum.VALIDEE);
            this.etatProlongementRepository.save(etatProlongement);
        }
    }

    // l’etat change du dernier etat sur la prolongement
    public boolean etatProlongementChange(long idProlongement, boolean confirmer) {
        // EtatProlongement etatProlongement = this.get
        EtatProlongement etatProlongement = this.getLastEtatProlongement(idProlongement);
        if (etatProlongement == null)
            return true;
        if (etatProlongement.getEtat().equals(EtatReservationEnum.VALIDEE) && confirmer ||
                etatProlongement.getEtat().equals(EtatReservationEnum.REFUSEE) && !confirmer)
            return false;
        return true;
    }

    public EtatProlongement getLastEtatProlongement(long idProlongement) {
        return this.etatProlongementRepository.findFirstByProlongementIdOrderByDateValidationDesc(idProlongement)
                .orElse(null);
    }

}
