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

import com.Entite.AdherentQuota;
import com.Entite.Livre;
import com.Entite.User;

@Repository
public interface AdherentQuotaRepository extends JpaRepository<AdherentQuota, Long> {
    @Query(value = "SELECT aq.quota FROM inscription AS i JOIN adherent_quota AS aq ON aq.id_type_adherent = i.id_type_adherent WHERE i.id = :idInscription", nativeQuery = true)
    public Integer getQuotaDepenseActuel(@Param("idInscription") Long idInscription);

}
