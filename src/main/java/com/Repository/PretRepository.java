package com.Repository;

import java.time.LocalDateTime;
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
import com.Entite.Pret;
import com.Entite.User;
import com.dto.PretParametreDTO;

@Repository
public interface PretRepository extends JpaRepository<Pret, Long> {
    @Query(value = "SELECT * FROM pret_parametre WHERE :dateCible BETWEEN date_pret AND date_fin_pret AND id_exemplaire = :idExemplaire", nativeQuery = true)
    public PretParametreDTO findPretWhereExemplaireIn(@Param("dateCible") LocalDateTime dateCible,
            @Param("idExemplaire") Long idExemplaire);
}
