package com.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Entite.Inscription;
import com.Entite.Livre;
import com.Entite.PretNombreDescView;
import com.Entite.User;
import com.Entite.UserCountPretDescView;
import com.Entite.Livre;
import com.Repository.AdherentQuotaRepository;
import com.Repository.LivreRepository;
import com.Repository.UserRepository;
import com.dto.PenaliteResponse;
import com.Repository.LivreRepository;

import jakarta.transaction.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

@Service
public class DashboardService {
    @Autowired
    private AdherentQuotaRepository adherentQuotaRepository;
    @Autowired
    private LivreService livreService;
    @Autowired
    private UserCountPretDescViewService userCountPretDescViewService;
    @Autowired
    private PretService pretService;
    @Autowired
    private UserService userService;
    @Autowired
    private InscriptionService inscriptionService;

    // Obtenir la liste des utilisateurs penalises
    public HashMap<User, String> getListeUserPenalise() throws Exception {
        HashMap<User, String> result = new HashMap<>();
        List<User> userList = userService.findAll();
        if (userList == null || userList.size() == 0)
            throw new Exception("Aucun utilisateur trouve");
        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            Inscription currInscription = inscriptionService.getInscription(user.getId());
            if (currInscription != null) {
                PenaliteResponse penaliteResponse = this.pretService.subitPenalite(currInscription.getId());
                if (penaliteResponse.isSubitPenalite())
                    result.put(user, penaliteResponse.getMessage());
            }
        }
        return result;
    }

    // Obtenir les utilisateurs qui on le plus pretes
    public List<UserCountPretDescView> findUserPlusPretes() {
        return this.userCountPretDescViewService.findAll();
    }

    // Obtenir les livres les plus pretes
    public List<PretNombreDescView> findLivresLesPlusPretes() throws Exception {
        List<PretNombreDescView> result = this.livreService.findLivresLesPlusPretes();
        if (result == null || result.size() == 0)
            throw new Exception("Erreurs lors de la recuperation des donnees statistiques");
        return result;
    }

}
