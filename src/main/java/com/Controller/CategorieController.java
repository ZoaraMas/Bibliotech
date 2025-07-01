package com.Controller;

import com.Entite.Categorie;
import com.Repository.CategorieRepository;
import com.Service.CategorieService;

import jakarta.servlet.RequestDispatcher;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/categorie")
public class CategorieController {

    @Autowired
    private CategorieService categorieService;

    // Formulaire: insert, update
    @GetMapping("/form-categorie")
    public String form(Model model, @RequestParam(name = "id", required = false) Long id) {
        if (id != null) {
            Categorie categorie = categorieService.findById(id);
            model.addAttribute(categorie);
        }
        return "categorie/form-categorie";
    }

    // Liste
    @GetMapping("/read-categorie")
    public String listCategorie(Model model) {
        model.addAttribute("categories", categorieService.findAll());
        return "categorie/list-categorie"; // resolves to /WEB-INF/views/previsions/list.jsp
    }

    // Create
    @PostMapping("/create-categorie")
    public String createCategorie(Model model, @RequestParam(name = "libelle", required = true) String libelle) {
        Categorie categorie = new Categorie(libelle);
        categorieService.save(categorie);
        return "categorie/form-categorie";
    }

    // Add more handler methods as needed
}