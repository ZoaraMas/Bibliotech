package com.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Entite.Exemplaire;
import com.Entite.Genre;
import com.Entite.Livre;
import com.Entite.User;
import com.Entite.Livre;
import com.Repository.ExemplaireRepository;
import com.Repository.LivreRepository;
import com.Repository.UserRepository;
import com.dto.ExemplaireDisponibilite;
import com.Repository.LivreRepository;

import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ExemplaireService {
    @Autowired
    private ExemplaireRepository exemplaireRepository;

    @Autowired
    private PretService pretService;

    // Alea: obtenir tout les exemplaires pour un livre
    public List<ExemplaireDisponibilite> findAllDtoByLivreId(Long idLivre) {
        return this.exemplaireRepository.findAllDtoByLivreId(idLivre);

    }

    public List<Exemplaire> findAllByLivreId(Long idLivre) {
        return this.exemplaireRepository.findAllByLivreId(idLivre);

    }

    public Genre getGenreFromIdExemplaire(Long idExemplaire) throws Exception {
        Exemplaire exemplaire = this.exemplaireRepository.findByIdWithLivreAndGenre(idExemplaire).orElse(null);
        if (exemplaire == null)
            throw new Exception("Exemplaire with id: " + idExemplaire + " not found");
        return exemplaire.getLivre().getGenre();
    }

    public Exemplaire findById(Long idExemplaire) {
        Exemplaire result = this.exemplaireRepository.findById(idExemplaire)
                .orElseThrow(() -> new IllegalArgumentException(
                        "L'exemplaire avec l'id ID: " + idExemplaire + " non trouve"));
        return result;
    }

    public boolean exemplaireDisponible(Long idExemplaire) throws Exception {
        return this.pretService.exemplaireEstDisponible(idExemplaire);
    }

    public boolean exemplaireDisponible(Long idExemplaire, LocalDateTime date) throws Exception {
        return this.pretService.exemplaireEstDisponible(idExemplaire, date);
    }

    public boolean exemplaireEstNonDisponible(Long idExemplaire) throws Exception {
        return this.pretService.exemplaireEstNonDisponible(idExemplaire);
    }

    public boolean exemplaireEstNonDisponible(Long idExemplaire, LocalDateTime date) throws Exception {
        return this.pretService.exemplaireEstNonDisponible(idExemplaire, date);
    }

    public boolean exemplaireExists(Long id) {
        return this.exemplaireRepository.existsById(id);
    }
}
