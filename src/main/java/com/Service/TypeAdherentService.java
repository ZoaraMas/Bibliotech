package com.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Entite.Exemplaire;
import com.Entite.Inscription;
import com.Entite.Pret;
import com.Entite.PretParametreView;
import com.Entite.Prolongement;
import com.Entite.Reservation;
import com.Entite.TypeAdherent;
import com.Entite.User;
import com.Repository.ProlongementRepository;
import com.Repository.TypeAdherentRepository;
import com.dto.PenaliteResponse;

import jakarta.transaction.Transactional;

@Service
public class TypeAdherentService {
    @Autowired
    private TypeAdherentRepository typeAdherentRepository;
    @Autowired
    private InscriptionService inscriptionService;

    // Obtenir le type adherent pour un user pour son inscription
    @Transactional
    public TypeAdherent getTypeAdherentUserFromInscription(Long idUser) throws Exception {
        Inscription inscription = inscriptionService.getCurrentInscription(idUser);
        return inscription.getTypeAdherent();
    }

    public TypeAdherent findById(Integer id) throws Exception {
        return this.typeAdherentRepository.findById(id)
                .orElseThrow(() -> new Exception("TypeAdherent: " + id + " non trouve"));
    }
}
