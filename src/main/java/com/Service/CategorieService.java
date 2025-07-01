package com.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Entite.Categorie;
import com.Repository.CategorieRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategorieService {
    @Autowired
    private CategorieRepository categorieRepository;

    public void save(Categorie categorie) {
        categorieRepository.save(categorie);
    }

    public List<Categorie> findAll() {
        return categorieRepository.findAll();
    }

    public Categorie findById(Long id) {
        Optional<Categorie> categorie = categorieRepository.findById(id);
        return categorie.orElse(null);
    }

    public Categorie findByLibelle(String libelle) {
        Optional<Categorie> categorie = categorieRepository.findByLibelle(libelle);
        return categorie.orElse(null);
    }
}
