package com.Controller;

import com.Entite.Categorie;
import com.Entite.Film;
import com.Repository.CategorieRepository;
import com.Service.CategorieService;
import com.Service.FilmService;
import com.Service.FilmService;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpSession;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/film")
public class FilmController {

    @Autowired
    private FilmService filmService;
    @Autowired
    private CategorieService categorieService;

    // Fiche
    @GetMapping("/fiche-film")
    public String ficheFilm(Model model, @RequestParam(name = "id", required = true) Long id) {
        model.addAttribute("films", filmService.findAll());
        Film film = filmService.findByIdWithCategories(id);
        model.addAttribute("film", film);
        return "film/fiche-film"; // resolves to /WEB-INF/views/previsions/list.jsp
    }

    // Formulaire: insert, update
    @GetMapping("/form-film")
    public String form(Model model, @RequestParam(name = "id", required = false) Long id) {
        if (id != null) {
            Film film = filmService.findByIdWithCategories(id);
            model.addAttribute(film);
        }
        List<Categorie> listeCategorie = categorieService.findAll();
        model.addAttribute("listeCategorie", listeCategorie);
        return "film/form-film";
    }

    // Liste
    @GetMapping("/read-film")
    public String listFilm(Model model, @RequestParam(name = "id", required = false) Long id) {
        model.addAttribute("films", filmService.findAll());
        if (id != null) {
            filmService.deleteById(id);
            model.addAttribute("films", filmService.findAll());
        }
        return "film/list-film"; // resolves to /WEB-INF/views/previsions/list.jsp
    }

    // Create
    @PostMapping("/create-film")
    public String createFilm(Model model, HttpSession session,
            @RequestParam(name = "id", required = false) Long id,
            @RequestParam(name = "nom", required = true) String nom,
            @RequestParam(name = "duree", required = true) float duree,
            @RequestParam(name = "idCategorie", required = true) List<Long> idCategories) {
        Film film = new Film(nom, duree);
        String redirect = "redirect:form-film";
        if (id != null) {
            film.setId(id);
            redirect = "redirect:read-film";
        }
        filmService.saveWithCategories(film, idCategories);
 
        return redirect;
    }

    // Add more handler methods as needed
}