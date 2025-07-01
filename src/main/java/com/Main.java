package com;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.Entite.Categorie;
import com.Entite.Film;
import com.Entite.FilmCategorie;
import com.Service.CategorieService;
import com.Service.FilmService;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Démarrage de l'application...");

        // Chargement du contexte Spring
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        FilmService filmService = context.getBean(FilmService.class);
        CategorieService categorieService = context.getBean(CategorieService.class);
        Film film1 = filmService.findByIdWithCategories(18l);
        Categorie action = categorieService.findById(1l);
        for (FilmCategorie fc : film1.getFilmCategories()) {
            System.out.println(fc.getCategorie().disp());
            if (fc.getCategorie().customEquals(action))
                System.out.println("found");
        }
        try {
        } catch (Exception e) {
            System.err.println("Erreur : " + e.getMessage());
            // e.printStackTrace();
        } finally {
            // Fermeture propre du contexte Spring (arrête aussi Hibernate et MySQL)
            context.close();
            System.out.println("Application terminée.");
        }
    }
}