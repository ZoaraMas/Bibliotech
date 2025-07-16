package com.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Entite.Livre;
import com.Entite.PretNombreDescView;
import com.Entite.User;
import com.Entite.Livre;
import com.Repository.LivreRepository;
import com.Repository.PretNombreDescViewRepository;
import com.Repository.UserRepository;
import com.Repository.LivreRepository;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PretNombreDescViewService {
    @Autowired
    private PretNombreDescViewRepository pretNombreDescViewRepository;

    // public List<PretNombreDescView> findLivresLesPlusPretes() {
    // return this.livreRepository.findLivresLesPlusPretes();
    // }

    // Verifier si le membre est actuellement inscrit
    public List<PretNombreDescView> findAll() {
        return this.pretNombreDescViewRepository.findAll();
    }
}
