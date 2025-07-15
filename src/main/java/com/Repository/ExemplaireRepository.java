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

import com.Entite.Exemplaire;
import com.dto.ExemplaireDisponibilite;
import com.Entite.Livre;
import com.Entite.User;

@Repository
public interface ExemplaireRepository extends JpaRepository<Exemplaire, Long> {
    @Query(value = "SELECT e FROM Exemplaire e JOIN FETCH e.livre l JOIN FETCH l.genre g WHERE e.id = :idExemplaire")
    public Optional<Exemplaire> findByIdWithLivreAndGenre(@Param("idExemplaire") Long idExemplaire);

    // Alea: obtenir tout les exemplaires pour un livre
    @Query("SELECT NEW com.dto.ExemplaireDisponibilite(e.id, e.reference, e.dateArrivee) "
            + " FROM Exemplaire e " + " JOIN e.livre l "
            + " WHERE l.id = :idLivre")
    public List<ExemplaireDisponibilite> findAllDtoByLivreId(@Param("idLivre") Long idLivre);

    @Query(value = "SELECT e FROM Exemplaire e JOIN FETCH e.livre l JOIN FETCH l.genre g WHERE l.id = :idLivre")
    public List<Exemplaire> findAllByLivreId(@Param("idLivre") Long idLivre);
}
