package com.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.Entite.Exemplaire;
import com.Entite.Inscription;
import com.Entite.Livre;
import com.Entite.Pret;
import com.Entite.PretParametreView;
import com.Entite.RemiseLivre;
import com.Entite.TypePret;
import com.Entite.User;
import com.Entite.Livre;
import com.Repository.LivreRepository;
import com.Repository.PretParametreViewRepository;
import com.Repository.UserRepository;
import com.Utility.MyDate;
import com.dto.PenaliteResponse;
import com.Repository.PretRepository;
import com.Repository.RemiseLivreRepository;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
// import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RemiseLivreService {
    @Autowired
    private RemiseLivreRepository remiseLivreRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ExemplaireService exemplaireService;
    @Autowired
    private PretService pretService;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private JourSpecialService jourSpecialService;

    @Transactional
    public void remettreUnExemplaireDeLivre(Long idExemplaire, long idEmploye, LocalDateTime dateRemise,
            String commentaire) throws Exception {
        System.out.println("daupin");

        // l’employe existe
        if (!this.userService.userExists(idEmploye))
            throw new IllegalArgumentException("L'employe avec l'id " + idEmploye + " n'existe pas.");
        System.out.println("requin");

        // l’exemplaire du livre existe
        if (!this.exemplaireService.exemplaireExists(idExemplaire))
            throw new IllegalArgumentException("L'exemplaire de livre avec l'id " + idExemplaire + " n'existe pas.");
        System.out.println("pits");

        // Exemplaire non disponible
        if (!exemplaireService.exemplaireEstNonDisponible(idExemplaire, dateRemise)) {
            throw new IllegalArgumentException("L'exemplaire de livre ID:" + idExemplaire
                    + " a deja ete rendu ou n'est encore prete maintenant, il y a erreur.");
        }
        System.out.println("mickey");
        // Date valide
        // if (!MyDate.dateValide(dateRemise)) {
        // throw new IllegalArgumentException("La date doit etre avant aujourd'hui");
        // }

        // Dans tout les cas, getPretFromExemplaireActuel qui va prendre le dernier pret
        // avec l'exemplaire sera toujours le dernier malgre
        // la possiblite de choix de date, en effet personne n'aurait pu repreter le
        // livre jusqu'a aujourd'hui.

        // Aussi les prets avec date superieur a aujourd'hui ne devraient pas exister,
        // changer la logique si on peut choisir la date d'un pret
        Pret pret = this.pretService.getPretFromExemplaireActuel(idExemplaire);
        System.out.println("Donald");
        User emp = userService.findById(idEmploye);
        System.out.println("Minnie");
        RemiseLivre remiseLivre = new RemiseLivre(pret, dateRemise, commentaire, emp);
        System.out.println("Pluto");

        // Le jour de pret n'est pas special
        if (this.jourSpecialService.isSpecial(dateRemise)) {
            throw new Exception("" + dateRemise + " Est soit un dimanche soit ferrie ");
        }

        this.remiseLivreRepository.save(remiseLivre);
        entityManager.flush();

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void remettreUnExemplaireDeLivreAvecNouvelleTransaction(Long idExemplaire, long idEmploye,
            LocalDateTime dateRemise, String commentaire) throws Exception {
        System.out.println("daupin");

        // l’employe existe
        if (!this.userService.userExists(idEmploye))
            throw new IllegalArgumentException("L'employe avec l'id " + idEmploye + " n'existe pas.");
        System.out.println("requin");

        // l’exemplaire du livre existe
        if (!this.exemplaireService.exemplaireExists(idExemplaire))
            throw new IllegalArgumentException("L'exemplaire de livre avec l'id " + idExemplaire + " n'existe pas.");
        System.out.println("pits");

        // Exemplaire non disponible
        if (!exemplaireService.exemplaireEstNonDisponible(idExemplaire, dateRemise)) {
            throw new IllegalArgumentException("L'exemplaire de livre ID:" + idExemplaire
                    + " a deja ete rendu ou n'est encore prete maintenant, il y a erreur.");
        }
        System.out.println("mickey");
        // Date valide
        // if (!MyDate.dateValide(dateRemise)) {
        // throw new IllegalArgumentException("La date doit etre avant aujourd'hui");
        // }

        // Dans tout les cas, getPretFromExemplaireActuel qui va prendre le dernier pret
        // avec l'exemplaire sera toujours le dernier malgre
        // la possiblite de choix de date, en effet personne n'aurait pu repreter le
        // livre jusqu'a aujourd'hui.

        // Aussi les prets avec date superieur a aujourd'hui ne devraient pas exister,
        // changer la logique si on peut choisir la date d'un pret
        Pret pret = this.pretService.getPretFromExemplaireActuel(idExemplaire);
        System.out.println("Donald");
        User emp = userService.findById(idEmploye);
        System.out.println("Minnie");
        RemiseLivre remiseLivre = new RemiseLivre(pret, dateRemise, commentaire, emp);
        System.out.println("Pluto");

        this.remiseLivreRepository.save(remiseLivre);
        entityManager.flush();
        // Même logique que remettreUnExemplaireDeLivre
    }

}
