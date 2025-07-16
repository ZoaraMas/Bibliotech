package com.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Entite.Exemplaire;
import com.Entite.Livre;
import com.Entite.TypePret;
import com.Entite.User;
import com.Entite.Livre;
import com.Repository.ExemplaireRepository;
import com.Repository.LivreRepository;
import com.Repository.TypePretRepository;
import com.Repository.UserRepository;
import com.Repository.LivreRepository;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TypePretService {
    @Autowired
    private TypePretRepository typePretRepository;

    public List<TypePret> findAll() {
        return this.typePretRepository.findAll();
    }

    public TypePret findById(Integer typePretId) {
        TypePret result = this.typePretRepository.findById(typePretId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Le type de pret avec ID: " + typePretId + " non trouve"));
        return result;
    }
}
