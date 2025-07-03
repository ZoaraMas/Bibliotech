package com.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.Entite.Livre;
import com.Entite.Pret;
import com.Entite.User;
import com.Entite.Livre;
import com.Repository.LivreRepository;
import com.Repository.UserRepository;
import com.dto.PretParametreDTO;
import com.Repository.PretRepository;

import jakarta.transaction.Transactional;

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

    // Fonctionalite 1
    // Preter un livre a un membre selon le type d’adherant, le type de livre et la
    // disponibilite du livre en elle meme.
    public void preterUnExemplaireLivre(long idUser, long idEmploye, long idExemplaire) {

    }

    // Verifier si le membre subit une penalite ou non
    public boolean subitPenalite(int idInscription) {
        int nombreJourPenalite = 0;
        List<PretParametreDTO> liste = this.pretRepository.getAllPretOrderByDateFinAsc(idInscription);
        return true;
    }

    public boolean quotaNonNull(int idInscription) {
        if (this.getQuotaRestant(idInscription) == 0)
            return false;
        return true;
    }

    public int getQuotaRestant(int idInscription) {
        return this.adherentQuotaService.getQuotaInscription(idInscription) - this.getNombrePretActuel(idInscription);
    }

    public int getNombrePretActuel(int idInscription) {
        LocalDateTime dateCible = LocalDateTime.now();
        Integer result = this.pretRepository.getQuotaDepenseActuel(dateCible, idInscription);
        return result;
    }

    public boolean exemplaireEstDisponible(Long idExemplaire) throws Exception {
        LocalDateTime dateCible = LocalDateTime.now();
        PretParametreDTO pretParametreDTO = this.pretRepository.findPretWhereExemplaireIn(dateCible, idExemplaire);
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
