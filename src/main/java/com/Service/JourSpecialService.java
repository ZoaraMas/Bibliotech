package com.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.Entite.Exemplaire;
import com.Entite.Genre;
import com.Entite.Inscription;
import com.Entite.JourSpecial;
import com.Entite.Livre;
import com.Entite.ParametrePret;
import com.Entite.Pret;
import com.Entite.PretParametreView;
import com.Entite.TypePret;
import com.Entite.User;
import com.Entite.Livre;
import com.Repository.JourSpecialRepository;
import com.Repository.LivreRepository;
import com.Repository.PretParametreViewRepository;
import com.Repository.UserRepository;
import com.dto.PenaliteResponse;
import com.Repository.PretRepository;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JourSpecialService {
    @Autowired
    private JourSpecialRepository jourSpecialRepository;

    public boolean isSpecial(LocalDateTime date) {
        return isSpecial(date.toLocalDate());
    }

    public boolean isSpecial(LocalDate date) {
        JourSpecial jourSpecial = this.jourSpecialRepository.findByDate(date).orElse(null);
        if (jourSpecial == null)
            return false;
        return true;
    }

    public List<JourSpecial> findAll() {
        return this.jourSpecialRepository.findAll();
    }

}