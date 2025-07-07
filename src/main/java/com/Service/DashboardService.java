package com.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Entite.Livre;
import com.Entite.User;
import com.Entite.Livre;
import com.Repository.AdherentQuotaRepository;
import com.Repository.LivreRepository;
import com.Repository.UserRepository;
import com.dto.LivreNombre;
import com.Repository.LivreRepository;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

@Service
public class DashboardService {
    @Autowired
    private AdherentQuotaRepository adherentQuotaRepository;
    @Autowired
    private LivreService livreService;

    public List<LivreNombre> findLivresLesPlusPretes() throws Exception {
        List<LivreNombre> result = this.livreService.findLivresLesPlusPretes();
        if (result == null || result.size() == 0)
            throw new Exception("Erreurs lors de la recuperation des donnees statistiques");
        return result;
    }

}
