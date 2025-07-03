package com.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Entite.Livre;
import com.Entite.User;
import com.Entite.Livre;
import com.Repository.LivreRepository;
import com.Repository.UserRepository;
import com.Repository.PretRepository;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PretService {
    @Autowired
    private PretRepository pretRepository ;
    
    @Autowired 
    private UserService userService;
    // Fonctionalite 1
    // Preter un livre a un membre selon le type d’adherant, le type de livre et la disponibilite du livre en elle meme.
    public void preterUnExemplaireLivre(long idUser, long idEmploye, long idExemplaire) {

    }

    // Verifier si le membre est actuellement inscrit
    public boolean membreEstInscrit() {
        return true;
    }

    public List<Livre> findAll() {
        return this.pretRepository.findAll();
    }
}
