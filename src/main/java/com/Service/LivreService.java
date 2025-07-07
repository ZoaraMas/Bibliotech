package com.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Entite.Livre;
import com.Entite.User;
import com.Entite.Livre;
import com.Repository.LivreRepository;
import com.Repository.UserRepository;
import com.dto.LivreNombre;
import com.Repository.LivreRepository;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LivreService {
    @Autowired
    private LivreRepository livreRepository;
    
    @Autowired 
    private UserService userService;

    public List<LivreNombre> findLivresLesPlusPretes() {
        return this.livreRepository.findLivresLesPlusPretes();
    }



    // Verifier si le membre est actuellement inscrit
    public boolean membreEstInscrit() {
        return true;
    }

    public List<Livre> findAll() {
        return this.livreRepository.findAll();
    }
}
