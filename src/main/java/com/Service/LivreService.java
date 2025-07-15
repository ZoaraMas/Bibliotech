package com.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Entite.Exemplaire;
import com.Entite.Livre;
import com.Entite.PretNombreDescView;
import com.Entite.User;
import com.Entite.Livre;
import com.Repository.LivreRepository;
import com.Repository.UserRepository;
import com.dto.ExemplaireDisponibilite;
import com.dto.LivreExemplairesDto;
import com.dto.LivreExemplairesDto;
import com.Repository.LivreRepository;

import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LivreService {
    @Autowired
    private LivreRepository livreRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PretNombreDescViewService pretNombreDescViewService;

    @Autowired
    private ExemplaireService exemplaireService;
    @Autowired
    private PretService pretService;

    // Obtenir les donnees d'un livre + tout les exemplaires avec leurs
    // disponibilites
    public LivreExemplairesDto getLivreWithExemplaires(Long idLivre) throws Exception {
        Livre livre = this.livreRepository.findByIdWithAllRelations(idLivre)
                .orElseThrow(() -> new Exception("Livre avec l'id: " + idLivre + " n'a pas ete trouve"));
        List<ExemplaireDisponibilite> listeExemplaire = this.exemplaireService.findAllDtoByLivreId(idLivre);
        ArrayList<ExemplaireDisponibilite> listeExemplaireAvecDisponibilite = new ArrayList<>();
        for (int i = 0; i < listeExemplaire.size(); i++) {
            ExemplaireDisponibilite e = listeExemplaire.get(i);
            String disponibilite = this.pretService.getDisponibiliteExemplaire(e.getId(), LocalDateTime.now());
            String message = disponibilite;
            boolean estDisponible = false;
            if (message == null) { // l'exemplaire est disponible
                message = "L'exemplaire est bel et bien disponible";
                estDisponible = true;
            }
            e.setDisponible(estDisponible);
            e.setMessage(message);
            // ExemplaireDisponibilite exemplaireDisponibilite = new
            // ExemplaireDisponibilite(e, estDisponible, message);
            listeExemplaireAvecDisponibilite.add(e);
        }
        LivreExemplairesDto result = new LivreExemplairesDto(livre, listeExemplaireAvecDisponibilite);
        return result;
    }

    // Obtenir un livre avec ses exemplaires
    public List<PretNombreDescView> findLivresLesPlusPretes() {
        return this.pretNombreDescViewService.findAll();
    }

    // Verifier si le membre est actuellement inscrit
    public boolean membreEstInscrit() {
        return true;
    }

    public List<Livre> findAll() {
        return this.livreRepository.findAll();
    }
}
