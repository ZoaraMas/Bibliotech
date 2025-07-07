package com.Repository;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.EntityGraph;
//  import org.springframework.data.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Entite.Livre;
import com.Entite.User;
import com.dto.LivreNombre;

@Repository
public interface LivreRepository extends JpaRepository<Livre, Long> {
    // Obtenir tout les livres avec nombre de pret trie decroissant
    @Query(value = "SELECT * FROM pret_nombre_desc 5", nativeQuery = true)
    public List<LivreNombre> findLivresLesPlusPretes();
}
