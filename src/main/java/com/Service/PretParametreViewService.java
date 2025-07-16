package com.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.Entite.Inscription;
import com.Entite.Livre;
import com.Entite.Pret;
import com.Entite.PretParametreView;
import com.Entite.User;
import com.Entite.Livre;
import com.Repository.LivreRepository;
import com.Repository.PretParametreViewRepository;
import com.Repository.UserRepository;
import com.Repository.PretRepository;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PretParametreViewService {
    @Autowired
    private PretParametreViewRepository pretParametreViewRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private AdherentQuotaService adherentQuotaService;

    // Verifier si il y a collision de pret
    public boolean ExistCollisionPret(LocalDateTime debut, LocalDateTime fin, Long idExemplaire) {
        List<PretParametreView> liste = this.pretParametreViewRepository.findPretCollisionSimulationDebutFin(debut, fin,
                idExemplaire);
        if (liste.size() == 0)
            return false;
        return true;
    }

    // Verifier si le pret a deja ete rendu ou non
    public boolean pretEstRendu(Long idPret) throws Exception {
        PretParametreView pretParametreView = this.pretParametreViewRepository.findById(idPret)
                .orElseThrow(() -> new Exception("Pret non trouve avec l'id: " + idPret));
        if (pretParametreView.getDateRemise() == null)
            return false;
        return true;
    }

    // Verifier que si la reservation devienne un pret plus tard, l'inscription
    // actuelle couvre tout le pret
    // public boolean pretEstCouvertDansInscription(Inscription inscription, )

    public PretParametreView findById(Long idPret) throws Exception {
        // Normalement al vue a la meme id que le pret
        return this.pretParametreViewRepository.findById(idPret)
                .orElseThrow(() -> new Exception("Pret avec parametres non trouve avec l'id: " + idPret));
    }

    public List<PretParametreView> findAll() {
        return this.pretParametreViewRepository.findAll();
    }

    public PretParametreView getCurrPretWithIdExemplaire(Long idExemplaire) throws Exception {
        List<PretParametreView> liste = this.pretParametreViewRepository
                .findPretByExemplaireWhereDateRemiseIsNotNullOrderByDatePretDesc(idExemplaire);
        if (liste == null)
            throw new Exception("L'id exemplaire ne semble pas etre prete en ce moment, veuillez contacter l'admin");
        if (liste.size() == 0)
            throw new Exception("L'id exemplaire ne semble pas etre prete en ce moment, veuillez contacter l'admin");
        return liste.get(0);
    }

    public List<PretParametreView> getAllPretOrderByDateFinAscByIdInscription(Long idInscription) {
        return this.pretParametreViewRepository.getAllPretOrderByDateFinAscByIdInscription(idInscription);
    }

    public PretParametreView findPretWhereExemplaireIn(LocalDateTime dateCible, Long idExemplaire) throws Exception {
        List<PretParametreView> liste = this.pretParametreViewRepository.findPretWhereExemplaireIn(dateCible,
                idExemplaire);
        if (liste.size() >= 2)
            throw new Exception(
                    "Erreur dans la base de donnee, il y a peut etre corruption de donnee, veuillez contacter l'admin");
        else if (liste.size() == 0)
            return null;
        return liste.get(0);
    }

    public Integer getQuotaDepenseActuel(LocalDateTime dateCible, Long idInscription) {
        return (this.pretParametreViewRepository.getQuotaDepenseActuel(dateCible, idInscription)).intValue();
    }

}
