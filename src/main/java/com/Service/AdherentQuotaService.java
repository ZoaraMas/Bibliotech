package com.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Entite.Livre;
import com.Entite.User;
import com.Entite.Livre;
import com.Repository.AdherentQuotaRepository;
import com.Repository.LivreRepository;
import com.Repository.UserRepository;
import com.Repository.LivreRepository;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AdherentQuotaService {
    @Autowired
    private AdherentQuotaRepository adherentQuotaRepository;

    public int getQuotaInscription(Long idInscription) {
        return this.adherentQuotaRepository.getQuotaDepenseActuel(idInscription);
    } 
}
