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

import com.Entite.Livre;
import com.Entite.Pret;
import com.Entite.PretParametreView;
import com.Entite.User;

@Repository
public interface PretParametreViewRepository extends JpaRepository<PretParametreView, Long> {
        // Obtenir un pret avec un idExemplaire qui est contenu totalement ou
        // partillement dans un pret
        @Query(value = "SELECT * FROM pret_parametre "
                        + " WHERE :dateDebutSimulation <= date_fin_pret "
                        + "   AND :dateFinSimulation >= date_pret "
                        + "   AND id_exemplaire = :idExemplaire", nativeQuery = true)
        public List<PretParametreView> findPretCollisionSimulationDebutFin(
                        @Param("dateDebutSimulation") LocalDateTime dateDebutCible,
                        @Param("dateFinSimulation") LocalDateTime dateFinCible,
                        @Param("idExemplaire") Long idExemplaire);

        // Obtenir un pr
        @Query("SELECT p FROM PretParametreView p WHERE p.idExemplaire = :idExemplaire AND p.dateRemise IS NULL ORDER BY p.datePret DESC")
        List<PretParametreView> findPretByExemplaireWhereDateRemiseIsNotNullOrderByDatePretDesc(
                        @Param("idExemplaire") Long idExemplaire);

        // obtenir tout les prets avec leurs parametres
        @Query(value = "SELECT * FROM pret_parametre WHERE id_inscription = :idInscription ORDER BY date_fin_pret ASC", nativeQuery = true)
        public List<PretParametreView> getAllPretOrderByDateFinAscByIdInscription(
                        @Param("idInscription") Long idInscription);

        // Version corrige qui prend en compte l'heure:
        @Query(value = "SELECT * FROM pret_parametre WHERE :dateCible >= date_pret AND (date_remise IS NULL OR :dateCible <= date_remise) AND id_exemplaire = :idExemplaire", nativeQuery = true)
        public List<PretParametreView> findPretWhereExemplaireIn(@Param("dateCible") LocalDateTime dateCible,
                        @Param("idExemplaire") Long idExemplaire);
        // On ne prend pas encore en compte les remises
        // @Query(value = "SELECT * FROM pret_parametre WHERE :dateCible >= date_pret
        // AND (date_remise IS NULL OR :dateCible <= date_remise) AND id_exemplaire =
        // :idExemplaire", nativeQuery = true)
        // public PretParametreView findPretWhereExemplaireIn(@Param("dateCible")
        // LocalDateTime dateCible,
        // @Param("idExemplaire") Long idExemplaire);

        // on suppose ici que si date_remise n'est pas null, c'est que date_remise est
        // forcement avant aujourd'hui.
        @Query(value = "SELECT COUNT(*) FROM pret_parametre WHERE :dateCible >= date_pret AND (date_remise IS NULL OR date_remise > :dateCible) AND id_inscription = :idInscription", nativeQuery = true)
        public Long getQuotaDepenseActuel(@Param("dateCible") LocalDateTime dateCible,
                        @Param("idInscription") Long idInscription);
}
