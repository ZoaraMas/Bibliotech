package com.Repository;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.antlr.v4.runtime.atn.SemanticContext.AND;
import org.springframework.data.jpa.repository.EntityGraph;
//  import org.springframework.data.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Entite.Inscription;
import com.Entite.User;

@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, Long> {
    // Retourne l'inscription actuelle, verifier d'abord si le user est bien inscrit
    // avant de le faire
    @Query("SELECT i FROM Inscription i JOIN FETCH i.typeAdherent t WHERE i.user.id = :idUser ORDER BY i.dateInscription DESC")
    Optional<Inscription> findFirstByUserIdOrderByDateInscriptionDescWithTypeAdherent(@Param("idUser") Long idUser);

    // Retourne l'inscription d'un utilisateur qui est alors encore inscrit a ce
    // jour
    // Ancienne version sans date_fin
    // @Query(value = "SELECT * FROM inscription WHERE id_user = :id_user AND
    // DATE_ADD(date_inscription, INTERVAL duree_mois MONTH) > NOW()", nativeQuery =
    // true)
    // Inscription estActuellementInscrit(@Param("id_user") long idUser);

    @Query(value = "SELECT * FROM inscription WHERE id_user = :id_user AND " +
            "COALESCE(date_fin, DATE_ADD(date_inscription, INTERVAL duree_mois MONTH)) > NOW()", nativeQuery = true)
    Inscription estActuellementInscrit(@Param("id_user") long idUser);
}
