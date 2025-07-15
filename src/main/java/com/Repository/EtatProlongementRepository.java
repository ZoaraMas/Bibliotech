package com.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.SqlResultSetMapping;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.EntityGraph;
//  import org.springframework.data.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Entite.EtatProlongement;
import com.Entite.EtatReservation;
import com.Entite.Livre;
import com.Entite.Pret;
import com.Entite.PretParametreView;
import com.Entite.RemiseLivre;
import com.Entite.Reservation;
import com.Entite.User;

@Repository
public interface EtatProlongementRepository extends JpaRepository<EtatProlongement, Long> {
    // Obtenir le dernier etat
    public Optional<EtatProlongement> findFirstByProlongementIdOrderByDateValidationDesc(Long idReservation);
}
