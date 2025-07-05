package com.Service;

import org.springframework.beans.factory.annotation.Autowired;
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

import jakarta.transaction.Transactional;

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

    public void remettreUnExemplaireDeLivre(Long idExemplaire, long idEmploye, LocalDateTime dateRemise,
            String commentaire) throws Exception {
        // l’employe existe
        if (!this.userService.userExists(idEmploye))
            throw new IllegalArgumentException("L'employe avec l'id " + idEmploye + " n'existe pas.");

        // l’exemplaire du livre existe
        if (!this.exemplaireService.exemplaireExists(idExemplaire))
            throw new IllegalArgumentException("L'exemplaire de livre avec l'id " + idExemplaire + " n'existe pas.");

        // Exemplaire non disponible
        if (!exemplaireService.exemplaireEstNonDisponible(idExemplaire)) {
            throw new IllegalArgumentException("L'exemplaire de livre ID:" + idExemplaire
                    + " a deja ete rendu ou n'est encore prete maintenant, il y a erreur.");
        }
        // Date valide
        if (!MyDate.dateValide(dateRemise)) {
            throw new IllegalArgumentException("La date doit etre avant aujourd'hui");
        }

        Pret pret = this.pretService.getPretFromExemplaireActuel(idExemplaire);
        User emp = userService.findById(idEmploye);
        RemiseLivre remiseLivre = new RemiseLivre(pret, dateRemise, commentaire, emp);
        this.remiseLivreRepository.save(remiseLivre);
    }

}
