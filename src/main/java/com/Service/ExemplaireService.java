package com.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Entite.Exemplaire;
import com.Entite.Livre;
import com.Entite.User;
import com.Entite.Livre;
import com.Repository.ExemplaireRepository;
import com.Repository.LivreRepository;
import com.Repository.UserRepository;
import com.Repository.LivreRepository;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ExemplaireService {
    @Autowired
    private ExemplaireRepository exemplaireRepository;

    public boolean exemplaireExists(Long id) {
        return this.exemplaireRepository.existsById(id);
    }
}
