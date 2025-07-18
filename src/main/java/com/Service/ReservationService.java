package com.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

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
import com.Repository.LivreRepository;
import com.Repository.PretParametreViewRepository;
import com.Repository.UserRepository;
import com.Utility.MyDate;
import com.dto.PenaliteResponse;
import com.Repository.PretRepository;
import com.Repository.RemiseLivreRepository;
import com.Repository.ReservationRepository;

import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private InscriptionService inscriptionService;
    @Autowired
    private TypePretService typePretService;
    @Autowired
    private UserService userService;
    @Autowired
    private ExemplaireService exemplaireService;
    @Autowired
    private PretService pretService;
    @Autowired
    private ParametrePretService parametrePretService;

    // disponibilite du livre en elle meme.
    public void demanderReservation(long idUser, long idEmploye, long idExemplaire, Integer idTypePret,
            LocalDateTime dateVoulue, String commentaire)
            throws Exception {
        // Date valide -> date apres aujourd'hui
        if (!MyDate.dateValideReservation(dateVoulue)) {
            throw new IllegalArgumentException("La date de reservation doit etre apres aujourd'hui");
        }
        // le membre existe
        if (!userService.userExists(idUser)) {
            throw new Exception("L'utilisateur ID:" + idUser + " n'existe pas");
        }
        // l’exemplaire du livre existe
        if (!exemplaireService.exemplaireExists(idExemplaire)) {
            throw new Exception("L'exemplaire de livre ID:" + idExemplaire + " n'existe pas");
        }
        // Exemplaire disponible
        if (!exemplaireService.exemplaireDisponible(idExemplaire, dateVoulue)) {
            throw new Exception(
                    "L'exemplaire de livre ID:" + idExemplaire + " n'est pas encore disponible a la date voulue");
        }
        // Membre actuellement inscrit
        // Actuellement inscrit seulement car dans tout les cas, creer un pret necessite
        // que pendant la duree du pret, l'utilisatuer soit toujours inscrit
        if (!inscriptionService.estActuellementInscrit(idUser)) {
            throw new Exception("Actuellement l'utilisateur ID:" + idUser + " n'est pas inscrit");
        }

        // Verifier que si la reservation devienne un pret plus tard, l'inscription
        // actuelle couvre tout le pret

        // Le membre ne subit pas de penalite
        // Ici aussi, quand le pret sera creer a la date d, il fa faloir que le membre
        // n'ai pas de penalite ce jour la
        Inscription currInscription = inscriptionService.getCurrentInscription(idUser);
        // Subit une penalite maintenant
        PenaliteResponse penaliteResponse = this.pretService.subitPenalite(currInscription.getId());
        if (penaliteResponse.isSubitPenalite()) {
            throw new Exception("Vous etes actuellement penalise, " + penaliteResponse.getMessage());
        }
        // Subit une penalite lors du jour demande a reserver
        penaliteResponse = this.pretService.subitPenalite(currInscription.getId(), dateVoulue);
        if (penaliteResponse.isSubitPenalite()) {
            throw new Exception("Vous serez encore penalise a la date voulue, " + penaliteResponse.getMessage());
        }

        TypePret typePret = this.typePretService.findById(idTypePret);
        // Le membre n’a pas encore termine tout son quota
        // Si il prend sur place, la regle ne s'applique pas
        // C'est pas grave si le quota est insuffisant maintenant, mais il doit l'etre
        // le jour voulue
        if (!this.pretService.quotaNonNull(currInscription.getId(), dateVoulue) && idTypePret != 2) {
            throw new Exception(
                    "Quota insuffisant, le jour du pret voulue, veuillez rendre un livre ou la fin du pret est apres cette date");
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

        Genre genreFromExemplaire = this.exemplaireService.getGenreFromIdExemplaire(idExemplaire);
        // L'inscription couvre tout le pret
        if (!this.parametrePretService.pretCouvertEntierementDansInscription(currInscription.getTypeAdherent(),
                typePret, genreFromExemplaire, currInscription, dateVoulue)) {
            throw new Exception("Impossible de reserver le livre a la date " + dateVoulue
                    + ", le pret n'est pas couvert par l'inscription.");
        }

        Reservation reservation = new Reservation(currInscription, exemplaire, typePret, dateVoulue, commentaire,
                employe);
        this.reservationRepository.save(reservation);
    }

    public Reservation findByIdWithAllRelations(long idReservation) {
        Optional<Reservation> reservation = reservationRepository.findByIdWithAllRelations(idReservation);
        if (reservation.isPresent()) {
            return reservation.get();
        } else {
            throw new RuntimeException("Reservation avec ID: " + idReservation + " n'existe pas");
        }
    }

    public boolean reservationExiste(Long idReservation) {
        return this.reservationRepository.existsById(idReservation);
    }
}
