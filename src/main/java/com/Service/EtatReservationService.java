package com.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.Controller.EtatReservationController;
import com.Entite.EtatReservation;
import com.Entite.Exemplaire;
import com.Entite.Genre;
import com.Entite.Inscription;
import com.Entite.Livre;
import com.Entite.Pret;
import com.Entite.PretParametreView;
import com.Entite.RemiseLivre;
import com.Entite.TypePret;
import com.Entite.User;
import com.Entite.Reservation;
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

import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EtatReservationService {
    @Autowired
    private EtatReservationRepository etatReservationRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private PretService pretService;

    @Transactional
    public void ajouterEtatReservation(Long idEmp, Long idReservation, Boolean confirmer, String commentaire)
            throws Exception {
        // l'employe existe
        if (!userService.userExists(idEmp)) {
            throw new IllegalArgumentException("L'utilisateur employe ID:" + idEmp + " n'existe pas");
        }
        // la reservation existe
        if (!this.reservationService.reservationExiste(idReservation)) {
            throw new IllegalArgumentException("La reservation avec l'ID:" + idReservation + " n'existe pas");
        }
        // l’etat change du dernier etat sur la reservation
        if (!etatReservationChange(idReservation, confirmer))
            if (confirmer)
                throw new IllegalArgumentException("La reservation :" + idReservation + " a deja ete confirme");
            else
                throw new IllegalArgumentException("La reservation :" + idReservation + " a deja ete refuse");
        User employe = this.userService.findById(idEmp);
        // Maintenant on peut s'occuper de l'acceptation/refus
        // Si refus, refuser tout simplement
        Reservation reservation = reservationService.findByIdWithAllRelations(idReservation);
        LocalDateTime dateChangement = LocalDateTime.now();
        EtatReservationEnum etat = EtatReservationEnum.REFUSEE;
        EtatReservation etatReservation = new EtatReservation(reservation, dateChangement, etat, commentaire, employe);
        if (!confirmer) {
            this.etatReservationRepository.save(etatReservation);
        } else {
            // Validation requiert les regles de gestions de pret + date choisie
            // On prepare les argumens propers a prets
            LocalDateTime dateVoulue = reservation.getDateReservation();
            long idUser = reservation.getInscription().getUser().getId();
            long idExemplaire = reservation.getExemplaire().getId();
            Integer idTypePret = reservation.getTypePret().getId();
            this.pretService.preterUnExemplaireLivre(idUser, idUser, idExemplaire, idTypePret, dateVoulue);
            etatReservation.setEtat(EtatReservationEnum.VALIDEE);
            this.etatReservationRepository.save(etatReservation);
        }
    }

    // l’etat change du dernier etat sur la reservation
    public boolean etatReservationChange(long idReservation, boolean confirmer) {
        EtatReservation etatReservation = this.getLastEtatReservation(idReservation);
        if (etatReservation == null)
            return true;
        if (etatReservation.getEtat().equals(EtatReservationEnum.VALIDEE) && confirmer ||
                etatReservation.getEtat().equals(EtatReservationEnum.REFUSEE) && !confirmer)
            return false;
        return true;
    }

    public EtatReservation getLastEtatReservation(long idReservation) {
        return this.etatReservationRepository.findFirstByReservationIdOrderByDateChangementDesc(idReservation)
                .orElse(null);
    }

}
