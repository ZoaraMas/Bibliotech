package com.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Entite.Prolongement;
import com.Entite.Reservation;

@Repository
public interface ProlongementRepository extends JpaRepository<Prolongement, Long> {
    public Optional<Prolongement> findByPretId(Long PretId);

    @Query("SELECT pp FROM Prolongement pp " +
            "JOIN FETCH pp.pret p " +
            "JOIN FETCH p.inscription i " +
            "JOIN FETCH i.user " +
            "JOIN FETCH p.exemplaire " +
            "JOIN FETCH p.typePret " +
            "JOIN FETCH p.employe " +
            "WHERE pp.id = :id")
    Optional<Prolongement> findByIdWithAllRelations(@Param("id") Long id);
}
