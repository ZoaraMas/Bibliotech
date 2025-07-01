package com.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Entite.Categorie;
import com.Entite.Film;
import com.Entite.FilmCategorie;
import com.Repository.CategorieRepository;
import com.Repository.FilmRepository;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FilmService {
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private CategorieRepository categorieRepository;

    @Transactional()
    public Film findByIdWithCategories(Long id) {
        Optional<Film> optionalfilm = filmRepository.findById(id);
        Film film = optionalfilm.orElse(null);
        film.getFilmCategories().size(); // initialiser
        for (FilmCategorie fc : film.getFilmCategories()) {
            fc.getCategorie().getLibelle();
        }
        return film;
    }

    @Transactional
    public Film saveWithCategories(Film film, List<Long> listeIdCategorie) {
        film.removeAllFilmCategories();
        List<Categorie> listeCategorie = categorieRepository.findAllById(listeIdCategorie);
        // Create the filmCategorie assocations
        for (Categorie c : listeCategorie) {
            FilmCategorie filmCategorie = new FilmCategorie();
            filmCategorie.setFilmAndCategory(film, c);
            film.addFilmCategorie(filmCategorie);
            System.out.println(c.disp());
        }
        System.out.println("saving");
        return filmRepository.save(film);
    }

    public void save(Film Film) {
        filmRepository.save(Film);
    }

    public void deleteById(Long id) {
        Film film = filmRepository.findById(id).orElse(null);
        if (film == null) {
            throw new RuntimeException("film not found");
        }
        filmRepository.delete(film);
    }

    public List<Film> findAll() {
        return filmRepository.findAll();
    }

    public Film findById(Long id) {
        Optional<Film> film = filmRepository.findById(id);
        return film.orElse(null);
    }

}
