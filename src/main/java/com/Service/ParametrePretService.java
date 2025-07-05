package com.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Entite.Genre;
import com.Entite.Inscription;
import com.Entite.Livre;
import com.Entite.ParametrePret;
import com.Entite.Pret;
import com.Entite.TypeAdherent;
import com.Entite.TypePret;
import com.Entite.User;
import com.Entite.Livre;
import com.Repository.AdherentQuotaRepository;
import com.Repository.LivreRepository;
import com.Repository.ParametrePretRepository;
import com.Repository.UserRepository;
import com.Repository.LivreRepository;

import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ParametrePretService {
    @Autowired
    private ParametrePretRepository parametrePretRepository;

    public boolean pretCouvertEntierementDansInscription(TypeAdherent typeAdherent, TypePret typePret, Genre genre,
            Inscription inscription, LocalDateTime datePrevu) throws Exception {
        ParametrePret parametrePret = this.findByParameters(typeAdherent, typePret, genre);
        if (parametrePret == null)
            throw new Exception("Parametre non trouve pour le pret/reservation");
        int nombreJour = parametrePret.getNbJourPret();
        LocalDate debutInscription = inscription.getDateInscription();
        LocalDate finInscription = debutInscription.plusMonths(inscription.getDureeMois());
        LocalDate debutPret = datePrevu.toLocalDate();
        LocalDate finPret = (datePrevu.plusDays(nombreJour)).toLocalDate();
        if (((debutPret.isAfter(debutInscription) || debutPret.equals(debutInscription))
                && (debutPret.isBefore(finInscription) || debutPret.equals(finInscription)) &&
                (finPret.isAfter(debutInscription) || finPret.equals(debutInscription))
                && finPret.isBefore(finInscription) || finPret.equals(finInscription))) {
            // Le pret est compris
            return true;
        }
        return false;
    }

    public ParametrePret findByParameters(TypeAdherent typeAdherent, TypePret typePret, Genre genre) {
        return this.parametrePretRepository.findByTypeAdherentAndTypePretAndGenre(typeAdherent, typePret, genre)
                .orElse(null);
    }
}
