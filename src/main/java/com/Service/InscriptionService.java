package com.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.Entite.Inscription;
import com.Entite.User;
import com.Repository.InscriptionRepository;
import com.Repository.UserRepository;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class InscriptionService {
    @Autowired
    private InscriptionRepository inscriptionRepository;
     // Retourne l'inscription d'un utilisateur qui est alors encore inscrit a ce jour
    public boolean estActuellementInscrit(long idUser) {
        Inscription inscription = this.inscriptionRepository.estActuellementInscrit(idUser);
        if(inscription == null) return false;
        return true;
    }
}
