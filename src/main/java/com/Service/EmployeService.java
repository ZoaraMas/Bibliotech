package com.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Entite.Categorie;
import com.Entite.Employe;
import com.Entite.Film;
import com.Entite.FilmCategorie;
import com.Repository.CategorieRepository;
import com.Repository.EmployeRepository;
import com.Repository.FilmRepository;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeService {
    @Autowired
    private EmployeRepository employeRepository;

    public Employe login(String mail, String password) {
        List<Employe> liste = this.findAll();
        for(int i = 0; i < liste.size(); i ++) {
            if(liste.get(i).loginMatch(mail, password)) return liste.get(i);
        }
        return null;
    }
    
    public List<Employe> findAll() {
        return this.employeRepository.findAll();
    }
}
