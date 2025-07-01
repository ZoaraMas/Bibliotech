package com.Repository;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
//  import org.springframework.data.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.Entite.Categorie;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Long> {
    Optional<Categorie> findByLibelle(String libelle);
}
