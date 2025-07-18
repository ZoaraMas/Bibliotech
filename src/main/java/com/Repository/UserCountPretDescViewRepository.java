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
import com.Entite.PretNombreDescView;
import com.Entite.User;
import com.Entite.UserCountPretDescView;

@Repository
public interface UserCountPretDescViewRepository extends JpaRepository<UserCountPretDescView, Long> {
    // Obtenir tout les livres avec nombre de pret trie decroissant
    // @Query(value = "SELECT id, titre, nombre_pret AS nombre FROM
    // pret_nombre_desc", nativeQuery = true)
    public List<UserCountPretDescView> findAll();
}
